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
 * @author Stephen Legge - Initial contribution
 */
public class HubDevices {

    private HubDevice[] data;
    private String[] errors;
    private HubDevicesPagination pagination;

    public HubDevices() {
        // Empty constructor for Gson.
    }

    public HubDevice[] getData() {
        return data;
    }

    public void setData(final HubDevice[] data) {
        this.data = data;
    }

    public String[] getErrors() {
        return errors;
    }

    public void setErrors(final String[] errors) {
        this.errors = errors;
    }

    public HubDevicesPagination getPagination() {
        return pagination;
    }

    public void setPagination(final HubDevicesPagination pagination) {
        this.pagination = pagination;
    }
}
