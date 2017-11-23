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

public class HubDeviceState {

    private boolean connection;

    private boolean powered;

    private String brightness;

    private String hue;

    private String saturation;

    @SerializedName("color_x")
    private String colorX;

    @SerializedName("color_y")
    private String colorY;

    @SerializedName("color_temperature")
    private String colorTemperature;

    @SerializedName("color_model")
    private String colorModel;

    private boolean locked;

    private String battery;

    @SerializedName("beeper_enabled")
    private boolean beeperEnabled;

    @SerializedName("vacation_mode_enabled")
    private boolean vacationModeEnabled;

    @SerializedName("alarm_mode")
    private String alarmMode;

    @SerializedName("alarm_sensitivity")
    private String alarmSensitivity;

    @SerializedName("auto_lock_enabled")
    private boolean autoLockEnabled;

    @SerializedName("alarm_enabled")
    private boolean alarmEnabled;

    public HubDeviceState() {
        // Empty constructor for Gson.
    }

    public final boolean isConnection() {
        return connection;
    }

    public final void setConnection(final boolean connection) {
        this.connection = connection;
    }

    public final boolean isPowered() {
        return powered;
    }

    public final void setPowered(final boolean powered) {
        this.powered = powered;
    }

    public final String getBrightness() {
        return brightness;
    }

    public final void setBrightness(final String brightness) {
        this.brightness = brightness;
    }

    public final String getHue() {
        return hue;
    }

    public final void setHue(final String hue) {
        this.hue = hue;
    }

    public final String getSaturation() {
        return saturation;
    }

    public final void setSaturation(final String saturation) {
        this.saturation = saturation;
    }

    public final String getColorX() {
        return colorX;
    }

    public final void setColorX(final String colorX) {
        this.colorX = colorX;
    }

    public final String getColorY() {
        return colorY;
    }

    public final void setColorY(final String colorY) {
        this.colorY = colorY;
    }

    public final String getColorTemperature() {
        return colorTemperature;
    }

    public final void setColorTemperature(final String colorTemperature) {
        this.colorTemperature = colorTemperature;
    }

    public final String getColorModel() {
        return colorModel;
    }

    public final void setColorModel(final String colorModel) {
        this.colorModel = colorModel;
    }

    public final boolean isLocked() {
        return locked;
    }

    public final void setLocked(final boolean locked) {
        this.locked = locked;
    }

    public final String getBattery() {
        return battery;
    }

    public final void setBattery(final String battery) {
        this.battery = battery;
    }

    public final boolean isBeeperEnabled() {
        return beeperEnabled;
    }

    public final void setBeeperEnabled(final boolean beeperEnabled) {
        this.beeperEnabled = beeperEnabled;
    }

    public final boolean isVacationModeEnabled() {
        return vacationModeEnabled;
    }

    public final void setVacationModeEnabled(final boolean vacationModeEnabled) {
        this.vacationModeEnabled = vacationModeEnabled;
    }

    public final String getAlarmMode() {
        return alarmMode;
    }

    public final void setAlarmMode(final String alarmMode) {
        this.alarmMode = alarmMode;
    }

    public final String getAlarmSensitivity() {
        return alarmSensitivity;
    }

    public final void setAlarmSensitivity(final String alarmSensitivity) {
        this.alarmSensitivity = alarmSensitivity;
    }

    public final boolean isAutoLockEnabled() {
        return autoLockEnabled;
    }

    public final void setAutoLockEnabled(final boolean autoLockEnabled) {
        this.autoLockEnabled = autoLockEnabled;
    }

    public final boolean isAlarmEnabled() {
        return alarmEnabled;
    }

    public final void setAlarmEnabled(final boolean alarmEnabled) {
        this.alarmEnabled = alarmEnabled;
    }
}
