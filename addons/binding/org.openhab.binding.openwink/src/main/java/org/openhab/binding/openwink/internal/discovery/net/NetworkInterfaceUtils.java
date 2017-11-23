/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openhab.binding.openwink.internal.discovery.net;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.TreeSet;

import org.apache.commons.net.util.SubnetUtils;

/**
 * Network utility functions for determining all interfaces and assigned IP addresses.
 *
 * @author David Graeff <david.graeff@web.de> - copied from network binding
 *
 * @author [ A Legge Up ] - Initial contribution
 * @author Stephen Legge <stephen@aleggeup.com>
 */
public class NetworkInterfaceUtils {

    /**
     * Gets every IPv4 Address on each Interface except the loopback
     * The Address format is ip/subnet
     *
     * @return The collected IPv4 Addresses
     */
    public static TreeSet<String> getInterfaceIPs() {
        final TreeSet<String> interfaceIPs = new TreeSet<String>();

        try {
            // For each interface ...
            for (final Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
                    .hasMoreElements();) {
                final NetworkInterface networkInterface = en.nextElement();
                if (!networkInterface.isLoopback()) {

                    // .. and for each address ...
                    for (final Iterator<InterfaceAddress> it = networkInterface.getInterfaceAddresses().iterator(); it
                            .hasNext();) {

                        // ... get IP and Subnet
                        final InterfaceAddress interfaceAddress = it.next();
                        interfaceIPs.add(interfaceAddress.getAddress().getHostAddress() + "/"
                                + interfaceAddress.getNetworkPrefixLength());
                    }
                }
            }
        } catch (final SocketException e) {
        }

        return interfaceIPs;
    }

    /**
     * Takes the interfaceIPs and fetches every IP which can be assigned on their network
     *
     * @param networkIPs The IPs which are assigned to the Network Interfaces
     * @return Every single IP which can be assigned on the Networks the computer is connected to
     */
    public static LinkedHashSet<String> getNetworkIPs(final TreeSet<String> interfaceIPs) {
        final LinkedHashSet<String> networkIPs = new LinkedHashSet<String>();

        for (final Iterator<String> it = interfaceIPs.iterator(); it.hasNext();) {
            try {
                // gets every ip which can be assigned on the given network
                final SubnetUtils utils = new SubnetUtils(it.next());
                final String[] addresses = utils.getInfo().getAllAddresses();
                for (int i = 0; i < addresses.length; i++) {
                    networkIPs.add(addresses[i]);
                }

            } catch (final Exception ex) {
            }
        }

        return networkIPs;
    }
}
