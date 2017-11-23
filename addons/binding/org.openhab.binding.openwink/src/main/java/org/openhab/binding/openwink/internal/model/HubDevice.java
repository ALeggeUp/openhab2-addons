/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openhab.binding.openwink.internal.model;

import com.google.gson.annotations.SerializedName;

public class HubDevice {

    @SerializedName("hub_device_id")
    private String hubDeviceId;

    @SerializedName("object_type")
    private String objectType;

    @SerializedName("discoverd_at")
    private String discoveredAt;

    @SerializedName("last_reading")
    private HubDeviceState lastReading;

    @SerializedName("desired_state")
    private HubDeviceState desiredState;

    @SerializedName("local_id")
    private String localId;

    @SerializedName("created_at")
    private String createdAt;

    private String name;

    @SerializedName("updated_at")
    private String updatedAt;

    public HubDevice() {
        // Empty constructor for Gson.
    }

    public String getHubDeviceId() {
        return hubDeviceId;
    }

    public void setHubDeviceId(final String hubDeviceId) {
        this.hubDeviceId = hubDeviceId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(final String objectType) {
        this.objectType = objectType;
    }

    public String getDiscoveredAt() {
        return discoveredAt;
    }

    public void setDiscoveredAt(final String discoveredAt) {
        this.discoveredAt = discoveredAt;
    }

    public HubDeviceState getLastReading() {
        return lastReading;
    }

    public void setLastReading(final HubDeviceState lastReading) {
        this.lastReading = lastReading;
    }

    public HubDeviceState getDesiredState() {
        return desiredState;
    }

    public void setDesiredState(final HubDeviceState desiredState) {
        this.desiredState = desiredState;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(final String localId) {
        this.localId = localId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
