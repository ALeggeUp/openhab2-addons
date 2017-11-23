/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openhab.binding.openwink.internal.discovery;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.openhab.binding.openwink.OpenWinkBindingConstants;
import org.openhab.binding.openwink.internal.discovery.net.NetworkInterfaceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OpwinWink discovery service searches the local network for the
 * "AAU" service running on port 8888 of the Wink Hub.
 *
 * @author [ A Legge Up ] - Initial contribution
 * @author Stephen Legge <stephen@aleggeup.com>
 * @author Stephen
 */
public class OpenWinkBridgeDiscoveryService extends AbstractDiscoveryService implements DiscoveryCallback {

    private Logger logger = LoggerFactory.getLogger(OpenWinkBridgeDiscoveryService.class);

    private static final long REFRESH = 300;
    private static final int TIMEOUT = 30;

    private ScheduledFuture<?> discoveryFuture;

    private ExecutorService executorService = null;

    public OpenWinkBridgeDiscoveryService() {
        super(OpenWinkBindingConstants.SUPPORTED_THING_TYPES_UIDS, TIMEOUT, true);
        logger.info("OpenWinkBridgeDiscoveryService");
    }

    @Override
    public Set<ThingTypeUID> getSupportedThingTypes() {
        return OpenWinkBindingConstants.SUPPORTED_THING_TYPES_UIDS;
    }

    @Override
    public boolean isBackgroundDiscoveryEnabled() {
        return true;
    }

    @Override
    protected void activate(final Map<String, Object> configProperties) {
        logger.info("StartScan called from activate");
        for (final Entry<String, Object> entry : configProperties.entrySet()) {
            logger.info(entry.getKey() + ": " + entry.getValue().toString());
        }
        startScan();
    }

    @Override
    protected void startBackgroundDiscovery() {
        logger.info("Start OpenWink Hub background discovery");
        if (discoveryFuture == null || discoveryFuture.isCancelled()) {
            discoveryFuture = scheduler.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    startScan();
                }
            }, 30, REFRESH, TimeUnit.SECONDS);
        }
    }

    /**
     * Starts the DiscoveryThread for each IP on each interface on the network
     *
     */
    @Override
    protected void startScan() {
        if (executorService != null) {
            stopScan();
        }

        logger.debug("Starting Discovery");
        final LinkedHashSet<String> networkIPs = NetworkInterfaceUtils
                .getNetworkIPs(NetworkInterfaceUtils.getInterfaceIPs());
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 10);

        for (final Iterator<String> it = networkIPs.iterator(); it.hasNext();) {
            final String ip = it.next();
            executorService.execute(new WinkSearchRunnable(ip, this));
        }
        stopScan();
    }

    @Override
    public void discoveredDevice(final String uuid, final String ip, final int port) {
        final DiscoveryResult result = DiscoveryResultBuilder
                .create(new ThingUID(OpenWinkBindingConstants.THING_TYPE_OPENWINK_HUB, uuid))
                .withLabel("Wink Hub (Bridge)").withProperty(OpenWinkBindingConstants.PARAMETER_HOST, ip)
                .withProperty(OpenWinkBindingConstants.PARAMETER_PORT, port).build();

        thingDiscovered(result);
    }
}
