/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openhab.binding.openwink.internal.discovery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.openhab.binding.openwink.internal.discovery.net.NonVerifyingHostnameVerifier;
import org.openhab.binding.openwink.internal.discovery.net.VeryTrustingTrustManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This runnable takes an ip address and tries to connect using SSL on port 8888
 * which is the "AAU" port on the Wink Hub. It expects an XML response and does
 * a simple pattern match on the lines to find the uuid and notifies the discovery
 * service through a callback that it has discovered a Wink Hub.
 *
 * @author [ A Legge Up ] - Initial contribution
 * @author Stephen Legge <stephen@aleggeup.com>
 */
class WinkSearchRunnable implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(WinkSearchRunnable.class);

    private final String ip;
    private final DiscoveryCallback callback;

    private TrustManager[] trustManagers = new TrustManager[] { new VeryTrustingTrustManager() };
    private HostnameVerifier hostnameVerifier = new NonVerifyingHostnameVerifier();

    private String uuid;

    public WinkSearchRunnable(final String ip, final DiscoveryCallback callback) {
        this.ip = ip;
        this.callback = callback;

        if (ip == null) {
            throw new RuntimeException("ip may not be null!");
        }
    }

    @Override
    public void run() {

        final String https_url = String.format("https://%s:%d/", this.ip, 8888);

        try {
            final SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, this.trustManagers, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(this.hostnameVerifier);

            final URL url = new URL(https_url);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            if (content(con) && this.uuid != null) {
                this.callback.discoveredDevice(this.uuid, this.ip, 8888);
            }

        } catch (final Exception e) {
            logger.info("{} exception", this.ip);
        }
    }

    /**
     * Expected response:
     *
     * {@code
     * <?xml version="1.0"?>
     * <root xmlns="urn:schemas-wink-com:device-1-0">
     *   <specVersion>
     *     <major>1</major>
     *     <minor>0</minor>
     *   </specVersion>
     *   <URLBase>https://192.168.2.30:8888</URLBase>
     *   <device>
     *     <deviceType>urn:wink-com:device:hub:2</deviceType>
     *     <friendlyName>Wink Hub</friendlyName>
     *     <manufacturer>Wink, Inc.</manufacturer>
     *     <manufacturerURL>http://www.wink.com</manufacturerURL>
     *     <modelDescription>Wink Hub</modelDescription>
     *     <modelName>Wink Hub</modelName>
     *     <modelNumber/>
     *     <modelURL>http://www.wink.com</modelURL>
     *     <serialNumber/>
     *     <UDN>uuid:964dcca3-012f-41ce-8a92-017b6d1812c8</UDN>
     *     <UPC/>
     *     <iconList/>
     *     <presentationURL/>
     *     <serviceList>
     *       <service>
     *         <serviceType>urn:wink-com:service:fasterLights:2</serviceType>
     *         <serviceId>urn:wink-com:serviceId:fasterLights</serviceId>
     *         <controlURL/>
     *       </service>
     *     </serviceList>
     *   </device>
     * </root>
     * }
     */
    private boolean content(final HttpsURLConnection connection) {

        boolean success = false;
        Exception exceptionThrown = null;

        if (connection != null) {

            try {
                final BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String input;

                final Pattern pattern = Pattern.compile("^<UDN>uuid:([0-9a-fA-F\\-]*)</UDN>$");

                while ((input = br.readLine()) != null) {
                    final Matcher matcher = pattern.matcher(input);
                    if (matcher.matches()) {
                        logger.info(input);
                        this.uuid = matcher.group(1);
                    }
                }
                br.close();

            } catch (final ConnectException e) {
                exceptionThrown = e;
            } catch (final IOException e) {
                exceptionThrown = e;
            } finally {
                success = exceptionThrown == null;
            }
        }

        return success;
    }
}
