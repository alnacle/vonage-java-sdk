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
package com.vonage.client.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.vonage.client.Jsonable;
import com.vonage.client.JsonableBaseObject;
import java.math.BigDecimal;
import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PricingResponse extends JsonableBaseObject {
    private String dialingPrefix;
    private BigDecimal defaultPrice;
    private String currency;
    @JsonUnwrapped private Country country;
    private List<Network> networks;

    @JsonProperty("dialingPrefix")
    public String getDialingPrefix() {
        return dialingPrefix;
    }

    @JsonProperty("defaultPrice")
    public BigDecimal getDefaultPrice() {
        return defaultPrice;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("country")
    public Country getCountry() {
        return country;
    }

    @JsonProperty("networks")
    public List<Network> getNetworks() {
        return networks;
    }

    public static PricingResponse fromJson(String json) {
        return Jsonable.fromJson(json);
    }
}
