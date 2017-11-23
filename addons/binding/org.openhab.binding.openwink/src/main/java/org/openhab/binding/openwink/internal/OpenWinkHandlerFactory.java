/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openhab.binding.openwink.internal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.smarthome.config.discovery.DiscoveryService;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.thing.type.ChannelGroupType;
import org.eclipse.smarthome.core.thing.type.ChannelType;
import org.openhab.binding.openwink.OpenWinkBindingConstants;
import org.openhab.binding.openwink.handler.OpenWinkBridgeHandler;
import org.openhab.binding.openwink.handler.OpenWinkDeviceHandler;
import org.openhab.binding.openwink.internal.discovery.OpenWinkDeviceDiscoveryService;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link OpenWinkHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author [ A Legge Up ] - Initial contribution
 * @author Stephen Legge <stephen@aleggeup.com>
 */
public class OpenWinkHandlerFactory extends BaseThingHandlerFactory {

    private static final Logger logger = LoggerFactory.getLogger(OpenWinkHandlerFactory.class);

    private final static Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = new HashSet<ThingTypeUID>(Arrays.asList(
            OpenWinkBindingConstants.THING_TYPE_OPENWINK_HUB, OpenWinkBindingConstants.THING_TYPE_OPENWINK_DEVICE));

    private final Map<ThingUID, ServiceRegistration<?>> discoveryServiceRegs = new HashMap<>();

    private final List<ChannelType> channelTypes = new CopyOnWriteArrayList<ChannelType>();
    private final List<ChannelGroupType> channelGroupTypes = new CopyOnWriteArrayList<ChannelGroupType>();

    @Override
    public boolean supportsThingType(final ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    protected ThingHandler createHandler(final Thing thing) {
        final ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        if (thingTypeUID.equals(OpenWinkBindingConstants.THING_TYPE_OPENWINK_HUB)) {
            final ThingHandler bridgeHandler = new OpenWinkBridgeHandler((Bridge) thing, this);
            this.registerOpenWinkDeviceDiscoveryService(bridgeHandler);
            return bridgeHandler;
        } else if (thingTypeUID.equals(OpenWinkBindingConstants.THING_TYPE_OPENWINK_DEVICE)) {
            return new OpenWinkDeviceHandler(thing);
        }

        return null;
    }

    /**
     * Adds HarmonyHubHandler to the discovery service to find Harmony Devices
     *
     * @param harmonyHubHandler
     */
    private synchronized void registerOpenWinkDeviceDiscoveryService(final ThingHandler bridgeHandler) {
        final DiscoveryService discoveryService = new OpenWinkDeviceDiscoveryService(bridgeHandler);
        this.discoveryServiceRegs.put(bridgeHandler.getThing().getUID(), bundleContext
                .registerService(DiscoveryService.class.getName(), discoveryService, new Hashtable<String, Object>()));
    }
}
