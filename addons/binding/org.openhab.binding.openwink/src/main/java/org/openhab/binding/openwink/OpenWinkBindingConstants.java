/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openhab.binding.openwink;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link OpenWinkBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Stephen Legge - Initial contribution
 */
@NonNullByDefault
public class OpenWinkBindingConstants {

    private OpenWinkBindingConstants() {
        // Prevent instantiation of utility class.
    }

    public static final String BINDING_ID = "openwink";
    public static final String TYPE_ID_HUB = "hub";
    public static final String TYPE_ID_DEVICE = "hub-device";

    // List of all Thing Type UIDs
    public final static ThingTypeUID THING_TYPE_OPENWINK_HUB = new ThingTypeUID(BINDING_ID, TYPE_ID_HUB);
    public final static ThingTypeUID THING_TYPE_OPENWINK_DEVICE = new ThingTypeUID(BINDING_ID, TYPE_ID_DEVICE);
    public final static Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = new HashSet<>(
            Arrays.asList(THING_TYPE_OPENWINK_HUB, THING_TYPE_OPENWINK_DEVICE));

    // List of thing parameters names
    public final static String PARAMETER_HOST = "ipAddress";
    public final static String PARAMETER_PORT = "port";
    public final static String PARAMETER_AUTH_TOKEN = "authToken";

    // List of all Channel ids
    public final static String CHANNEL_CONNECT = "connect";

    // Module Properties
    public final static String PROPERTY_VERSION = "version";

    // Used for Discovery service
    public final static String MANUFACTURER = "Wink Inc.";
}
