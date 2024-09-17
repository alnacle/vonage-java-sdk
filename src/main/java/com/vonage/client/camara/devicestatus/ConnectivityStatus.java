/*
 *   Copyright 2024 Vonage
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.vonage.client.camara.devicestatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represents the connectivity status of a device.
 */
public enum ConnectivityStatus {
    /**
     * The device is connected to the network for Data usage.
     */
    CONNECTED_DATA,

    /**
     * The device is connected to the network for SMS usage.
     */
    CONNECTED_SMS,

    /**
     * The device is not connected.
     */
    NOT_CONNECTED;

    @JsonValue
    @Override
    public String toString() {
        return super.toString();
    }

    @JsonCreator
    public static ConnectivityStatus fromString(String value) {
        if (value == null) return null;
        return valueOf(value.toUpperCase());
    }
}
