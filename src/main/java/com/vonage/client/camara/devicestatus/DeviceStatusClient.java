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

import com.vonage.client.DynamicEndpoint;
import com.vonage.client.HttpWrapper;
import com.vonage.client.RestEndpoint;
import com.vonage.client.VonageClient;
import com.vonage.client.auth.camara.BackendAuthRequest;
import com.vonage.client.auth.camara.NetworkAuthMethod;
import com.vonage.client.auth.camara.NotApplicableScope;
import static com.vonage.client.auth.camara.NotApplicableScope.CONNECTIVITY_READ;
import static com.vonage.client.auth.camara.NotApplicableScope.ROAMING_READ;
import com.vonage.client.camara.CamaraResponseException;
import com.vonage.client.camara.NetworkApiClient;
import com.vonage.client.common.HttpMethod;
import java.time.Instant;

/**
 * A client for communicating with the Vonage Device Status API. The standard way to obtain an instance
 * of this class is to use {@link VonageClient#getDeviceStatusClient()}.
 */
public class DeviceStatusClient extends NetworkApiClient {
    final RestEndpoint<DeviceStatusRequest, ConnectivityStatusResponse> connectivity;
    final RestEndpoint<DeviceStatusRequest, RoamingStatusResponse> roaming;

    /**
     * Create a new DeviceStatusClient.
     *
     * @param wrapper Http Wrapper used to create requests.
     */
    public DeviceStatusClient(HttpWrapper wrapper) {
        super(wrapper);

        @SuppressWarnings("unchecked")
        class Endpoint<R> extends DynamicEndpoint<DeviceStatusRequest, R> {
            Endpoint(String path, NotApplicableScope scope, R... type) {
                super(DynamicEndpoint.<DeviceStatusRequest, R> builder(type)
                        .authMethod(NetworkAuthMethod.class)
                        .responseExceptionType(CamaraResponseException.class)
                        .requestMethod(HttpMethod.POST).wrapper(wrapper).pathGetter((de, req) -> {
                            setNetworkAuth(new BackendAuthRequest(req.getPhoneNumber(), scope));
                            return getCamaraBaseUri() + "device-status/v050/" + path;
                        })
                );
            }
        }

        connectivity = new Endpoint<>("connectivity", CONNECTIVITY_READ);
        roaming = new Endpoint<>("roaming", ROAMING_READ);
    }

    /**
     * Get the current connectivity status information
     *
     * @param phoneNumber Subscriber number in E.164 format (starting with country code). Optionally prefixed with '+'.
     *
     * @return {@code String} information about current connectivity Status
     * 
     * @throws CamaraResponseException If the request was unsuccessful. This could be for the following reasons:
     * <ul>
     *     <li><b>400</b>: Invalid request arguments.</li>
     *     <li><b>401</b>: Request not authenticated due to missing, invalid, or expired credentials.</li>
     *     <li><b>403</b>: Client does not have sufficient permissions to perform this action.</li>
     *     <li><b>404</b>: SIM Swap can't be checked because the phone number is unknown.</li>
     *     <li><b>409</b>: Another request is created for the same MSISDN.</li>
     *     <li><b>502</b>: Bad gateway.</li>
     * </ul>
     */
    public String getConnectivityStatus(String phoneNumber) {
        return connectivity.execute(new DeviceStatusRequest(phoneNumber)).getConnectivity();
    }

    /**
     * Get the current roaming status and the country information
     *
     * @param phoneNumber Subscriber number in E.164 format (starting with country code). Optionally prefixed with '+'.
     *
     * @return TBC
     * 
     * @throws CamaraResponseException If the request was unsuccessful. This could be for the following reasons:
	 * <ul>
     *     <li><b>400</b>: Invalid request arguments.</li>
     *     <li><b>401</b>: Request not authenticated due to missing, invalid, or expired credentials.</li>
     *     <li><b>403</b>: Client does not have sufficient permissions to perform this action.</li>
     *     <li><b>404</b>: SIM Swap can't be checked because the phone number is unknown.</li>
	 *     <li><b>409</b>: Another request is created for the same MSISDN.</li>
	 *     <li><b>502</b>: Bad gateway.</li>
	 * </ul>
     */
    public boolean getRoamingStatus(String phoneNumber) {
        return roaming.execute(new DeviceStatusRequest(phoneNumber)).getRoaming();
    }
}
