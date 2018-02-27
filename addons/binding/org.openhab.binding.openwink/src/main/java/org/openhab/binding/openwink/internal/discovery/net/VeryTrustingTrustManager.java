/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openhab.binding.openwink.internal.discovery.net;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * This is a very trusting {@link X509TrustManager} that will never throw
 * a {@link CertificateException} because it is so trusting and assumes that
 * if a certificate was not trustworthy it wouldn't appreciate being singled
 * out.
 *
 * @author [ A Legge Up ] - Initial contribution
 * @author Stephen Legge <stephen@aleggeup.com>
 */
public class VeryTrustingTrustManager implements X509TrustManager {

    @Override
    public void checkClientTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}
