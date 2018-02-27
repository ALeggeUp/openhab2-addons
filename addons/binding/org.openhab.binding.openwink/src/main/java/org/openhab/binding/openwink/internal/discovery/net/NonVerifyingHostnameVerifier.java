/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openhab.binding.openwink.internal.discovery.net;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * This {@link HostnameVerifier} implementation doesn't actually verify
 * anything, it will verify every hostname.
 *
 * @author [ A Legge Up ] - Initial contribution
 * @author Stephen Legge <stephen@aleggeup.com>
 */
public class NonVerifyingHostnameVerifier implements HostnameVerifier {

    @Override
    public boolean verify(final String hostname, final SSLSession session) {
        return true;
    }
}
