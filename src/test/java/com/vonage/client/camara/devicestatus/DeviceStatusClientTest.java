
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

import com.vonage.client.AbstractClientTest;
import com.vonage.client.RestEndpoint;
import com.vonage.client.TestUtils;
import com.vonage.client.auth.camara.*;
import static com.vonage.client.auth.camara.NotApplicableScope.CONNECTIVITY_READ;
import static com.vonage.client.auth.camara.NotApplicableScope.ROAMING_READ;
import com.vonage.client.camara.CamaraResponseException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

public class DeviceStatusClientTest extends AbstractClientTest<DeviceStatusClient> {
    final String phoneNumber = "+49 151 23456789";

    public DeviceStatusClientTest() {
        client = new DeviceStatusClient(wrapper);
    }

    void setAuth(NotApplicableScope type) {
        wrapper.getAuthCollection().add(new NetworkAuthMethod(
                new NetworkAuthClient(wrapper), new BackendAuthRequest(phoneNumber, type)
        ));
    }

    void assert403CamaraResponseException(Executable invocation) throws Exception {
        final int status = 403;
        String code = "PERMISSION_DENIED", responseJson = "{\"status\": " +
                status+", \"code\": \""+code+"\",\"message\":\"Test msg\"}";

        stubBackendNetworkResponse(status, responseJson);

        String failMsg = "Expected "+ CamaraResponseException.class.getSimpleName();

        try {
            invocation.execute();
            fail(failMsg);
        }
        catch (CamaraResponseException ex) {
            assertEquals(status, ex.getStatusCode());
        }
        catch (Throwable t) {
            fail(failMsg, t);
        }
    }

    @Test
    public void testConnectivityDeviceStatus() throws Exception {
        setAuth(CONNECTIVITY_READ);
        String status = "CONNECTED_DATA", connectedResponse = "{\"connectivityStatus\":\""+status+"\"}";

        stubBackendNetworkResponse(connectedResponse);
        assertEquals(status, client.getConnectivityStatus(phoneNumber));

        setAuth(CONNECTIVITY_READ);
        assert403CamaraResponseException(() -> client.getConnectivityStatus(phoneNumber));
    }

    @Test
    public void testRoamingDeviceStatus() throws Exception {
        setAuth(ROAMING_READ);
        boolean roaming = true;
        int countryCode = 234;
        String countryName = "DE", roamingResponse = "{\"roaming\":"+roaming+
                ",\"countryCode\":"+countryCode+",\"countryName\":[\""+countryName+"\"]}";

        stubBackendNetworkResponse(roamingResponse);
        RoamingStatusResponse response = client.getRoamingStatus(phoneNumber);
        assertEquals(roaming, response.getRoaming());
        assertEquals(countryCode, response.getCountryCode());
        assertEquals(countryName, response.getCountryName().getFirst());

        setAuth(ROAMING_READ);
        assert403CamaraResponseException(() -> client.getRoamingStatus(phoneNumber));
    }

    @Test
    public void testGetConnectivityStatusEndpoint() throws Exception {
        new DeviceStatusEndpointTestSpec<ConnectivityStatusResponse>() {
            @Override
            protected RestEndpoint<DeviceStatusRequest, ConnectivityStatusResponse> endpoint() {
                return client.connectivity;
            }

            @Override
            protected NotApplicableScope getScope() {
                return CONNECTIVITY_READ;
            }
        }
        .runTests();
    }

    @Test
    public void testGetRoamingStatusEndpoint() throws Exception {
        new DeviceStatusEndpointTestSpec<RoamingStatusResponse>() {
            @Override
            protected RestEndpoint<DeviceStatusRequest, RoamingStatusResponse> endpoint() {
                return client.roaming;
            }

            @Override
            protected NotApplicableScope getScope() {
                return ROAMING_READ;
            }
        }
        .runTests();
    }
}
