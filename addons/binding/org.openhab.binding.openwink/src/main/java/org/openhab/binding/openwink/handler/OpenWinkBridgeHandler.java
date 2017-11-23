/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openhab.binding.openwink.handler;

import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.binding.BaseBridgeHandler;
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.openwink.OpenWinkBindingConstants;
import org.openhab.binding.openwink.internal.OpenWinkHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link OpenWinkBridgeHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author [ A Legge Up ] - Initial contribution
 * @author Stephen Legge <stephen@aleggeup.com>
 */
public class OpenWinkBridgeHandler extends BaseBridgeHandler {

    private final Logger logger = LoggerFactory.getLogger(OpenWinkBridgeHandler.class);

    @SuppressWarnings("unused")
    private final OpenWinkHandlerFactory handlerFactory;

    public OpenWinkBridgeHandler(final Bridge bridge, final OpenWinkHandlerFactory handlerFactory) {
        super(bridge);
        this.handlerFactory = handlerFactory;
    }

    @Override
    public void handleCommand(final ChannelUID channelUID, final Command command) {
        logger.info("handleCommand " + command.toFullString());
        if (channelUID.getId().equals(OpenWinkBindingConstants.CHANNEL_CONNECT)) {
            // TODO: handle command

            // Note: if communication with thing fails for some reason,
            // indicate that by setting the status with detail information
            // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
            // "Could not control device at IP address x.x.x.x");
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

    @SuppressWarnings("unused")
    @Override
    public void initialize() {
        final String host = this.getConfig().get(OpenWinkBindingConstants.PARAMETER_HOST).toString();
        final int port = this.getConfigInt(this.getConfig(), OpenWinkBindingConstants.PARAMETER_PORT);
        final String authToken = getConfigString(this.getConfig(), OpenWinkBindingConstants.PARAMETER_AUTH_TOKEN);

        // TODO: Initialize the thing. If done set status to ONLINE to indicate proper working.
        // Long running initialization should be done asynchronously in background.
        if (host == null || host.isEmpty()) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, "No Host Specified");
        } else if ("".equals(authToken)) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                    "A token is required to complete the configuration of the hub.");
        } else {
            updateStatus(ThingStatus.ONLINE);
            // final OpenWinkHubMonitorService service = new OpenWinkHubMonitorService(host, port, authToken);
            // service.start(scheduler);
        }

        // Note: When initialization can NOT be done set the status with more details for further
        // analysis. See also class ThingStatusDetail for all available status details.
        // Add a description to give user information to understand why thing does not work
        // as expected. E.g.
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
        // "Can not access device as username and/or password are invalid");
    }
}
