/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openhab.binding.openwink.internal.discovery;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.openhab.binding.openwink.OpenWinkBindingConstants;
import org.openhab.binding.openwink.internal.model.HubDevice;
import org.openhab.binding.openwink.internal.model.HubDevices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * OpwinWink discovery service searches the local network for the
 * "AAU" service running on port 8888 of the Wink Hub.
 *
 * @author [ A Legge Up ] - Initial contribution
 * @author Stephen Legge <stephen@aleggeup.com>
 * @author Stephen
 */
public class OpenWinkDeviceDiscoveryService extends AbstractDiscoveryService {

    private static final Logger logger = LoggerFactory.getLogger(OpenWinkDeviceDiscoveryService.class);

    private static final int TIMEOUT = 60;

    private final ThingHandler bridgeHandler;
    private final HttpClient httpClient;

    public OpenWinkDeviceDiscoveryService(final ThingHandler bridgeHandler) throws IllegalArgumentException {
        super(OpenWinkBindingConstants.SUPPORTED_THING_TYPES_UIDS, TIMEOUT, true);
        this.bridgeHandler = bridgeHandler;
        httpClient = new HttpClient(new SslContextFactory(true));
        httpClient.setConnectTimeout(90 * 1000L);
        try {
            httpClient.start();
        } catch (final Exception e) {
            logger.error("Unable to start Http(s) Client", e);
        }
        logger.info("OpenWinkDeviceDiscoveryService");
    }

    @Override
    public Set<ThingTypeUID> getSupportedThingTypes() {
        return OpenWinkBindingConstants.SUPPORTED_THING_TYPES_UIDS;
    }

    @Override
    protected void startBackgroundDiscovery() {
        logger.info("StartScan called from startBackgroundDiscovery");
        startScan();
    }

    @Override
    protected void activate(final Map<String, Object> configProperties) {
        logger.info("StartScan called from activate");
        for (final Entry<String, Object> entry : configProperties.entrySet()) {
            logger.info("{}: {}", entry.getKey(), entry.getValue().toString());
        }
        startScan();
    }

    @Override
    protected void startScan() {
        logger.info("startScan");
        final Configuration configuration = bridgeHandler.getThing().getConfiguration();

        final String host = configuration.get(OpenWinkBindingConstants.PARAMETER_HOST).toString();
        final int port = this.getConfigInt(configuration, OpenWinkBindingConstants.PARAMETER_PORT);
        final String authToken = getConfigString(configuration, OpenWinkBindingConstants.PARAMETER_AUTH_TOKEN);

        final String https_url = String.format("https://%s:%d/devices", host, port);

        Request request = httpClient.newRequest(https_url).method(HttpMethod.GET).timeout(5, TimeUnit.SECONDS)
                .header(HttpHeader.AUTHORIZATION, "Bearer " + authToken)
                .header(HttpHeader.CONTENT_TYPE, "application/json").header(HttpHeader.ACCEPT, "application/json")
                .header(HttpHeader.ACCEPT_ENCODING, "gzip");

        ContentResponse response;
        try {
            response = request.send();
            final String content = response.getContentAsString();
            logger.info("Response {}", content);

            Gson gson = new GsonBuilder().create();
            HubDevices devices = gson.fromJson(content, HubDevices.class);

            for (final HubDevice hubDevice : devices.getData()) {
                final DiscoveryResult result = DiscoveryResultBuilder
                        .create(new ThingUID(OpenWinkBindingConstants.THING_TYPE_OPENWINK_DEVICE,
                                bridgeHandler.getThing().getUID(), hubDevice.getHubDeviceId()))
                        .withLabel(hubDevice.getName()).withBridge(bridgeHandler.getThing().getUID())
                        .withProperty("name", hubDevice.getName()).withProperty("localId", hubDevice.getLocalId())
                        .build();

                thingDiscovered(result);
            }

            logger.info("parsed devices count: {}", devices.getData().length);
        } catch (final InterruptedException | TimeoutException | ExecutionException e) {
            logger.error("Unable to complete request.", e);
        }
    }

    private String getConfigString(final Configuration configuration, final String key) {
        String value = "";
        final Object object = configuration.get(key);
        if (object != null) {
            value = object.toString();
        }

        return value;
    }

    private Integer getConfigInt(final Configuration configuration, final String key) {
        Integer value = null;
        final Object object = configuration.get(key);
        if (object != null) {
            value = Integer.parseUnsignedInt(object.toString());
        }

        return value;
    }
}
