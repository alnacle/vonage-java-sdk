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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vonage.client.Jsonable;
import com.vonage.client.common.HalPageResponse;
import java.util.List;

/**
 * HAL response for {@link AccountClient#listSecrets(String)}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListSecretsResponse extends HalPageResponse {
    @JsonProperty("_embedded") private Embedded _embedded;

    @JsonIgnoreProperties(ignoreUnknown = true)
    static final class Embedded {
        @JsonProperty("secrets") private List<SecretResponse> secrets;
    }

    /**
     * Gets the secrets contained in the {@code _embedded} resource.
     *
     * @return The list of {@linkplain SecretResponse}, or {@code null} if absent from the response.
     */
    @JsonIgnore
    public List<SecretResponse> getSecrets() {
        return _embedded != null ? _embedded.secrets: null;
    }

    public static ListSecretsResponse fromJson(String json) {
        return Jsonable.fromJson(json);
    }
}
