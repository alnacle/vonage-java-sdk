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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vonage.client.JsonableBaseObject;

import java.util.List;

public class RoamingStatusResponse extends JsonableBaseObject {
    private boolean roaming;
    private Integer countryCode;
    private List<String> countryName;

    /**
     * Get the current roaming status information
     *
     * @return roaming status of the device 
     */
    @JsonProperty("roaming")
    public boolean getRoaming() {
        return roaming;
    }

    /**
     * Get the country Code 
     *
     * @return country code 
     */
    @JsonProperty("countryCode")
    public Integer getCountryCode() {
        return countryCode;
    }

    /**
     * Get the country Name list 
     *
     * @return List of country names
     */
    @JsonProperty("countryName")
    public List<String> getCountryName() {
        return countryName;
    }
}
