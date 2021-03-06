/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openhab.binding.openwink.internal.model;

/**
 * @author [ A Legge Up ] - Initial contribution
 * @author Stephen Legge <stephen@aleggeup.com>
 */
public class HubDevicesPagination {

    private int count;

    public HubDevicesPagination() {
        // Empty constructor for Gson.
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }
}
