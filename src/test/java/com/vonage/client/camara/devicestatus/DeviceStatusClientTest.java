
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
import java.time.Instant;

public class DeviceStatusClientTest extends AbstractClientTest<DeviceStatusClient> {
    final String phoneNumber = "+49 151 23456789", invalidNumber = TestUtils.API_SECRET;

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
        final String connectedResponse = "{\"connectivityStatus\":\"CONNECTED_DATA\"}";

        stubBackendNetworkResponse(connectedResponse);
        assertEquals("CONNECTED_DATA", client.getConnectivityStatus(phoneNumber));
    }

    /*
    @Test
    public void testRetrieveDeviceStatusDate() throws Exception {
        setAuth(RETRIEVE_SIM_SWAP_DATE);

        var timestampStr = "2019-08-24T14:15:22Z";
        var timestamp = Instant.parse(timestampStr);
        var response = "{\"latestSimChange\": \""+timestampStr+"\"}";

        stubBackendNetworkResponse(response);
        assertEquals(timestamp, client.retrieveDeviceStatusDate(phoneNumber));

        stubResponseAndAssertThrows(timestampStr,
                () -> client.retrieveDeviceStatusDate(null),
                NullPointerException.class
        );

        stubResponseAndAssertThrows(timestampStr,
                () -> client.retrieveDeviceStatusDate(invalidNumber),
                IllegalArgumentException.class
        );

        assert403CamaraResponseException(() -> client.retrieveDeviceStatusDate(phoneNumber));
    }

    @Test
    public void testCheckEndpoint() throws Exception {
        new DeviceStatusEndpointTestSpec<CheckDeviceStatusResponse>() {
            final int maxAge = 560;

            @Override
            protected RestEndpoint<DeviceStatusRequest, CheckDeviceStatusResponse> endpoint() {
                return client.check;
            }

            @Override
            protected FraudPreventionDetectionScope getScope() {
                return CHECK_SIM_SWAP;
            }

            @Override
            protected DeviceStatusRequest sampleRequest() {
                return new DeviceStatusRequest(msisdn, 560);
            }

            @Override
            protected String sampleRequestBodyString() {
                return super.sampleRequestBodyString().replace("}", ",\"maxAge\":"+maxAge+"}");
            }
        }
        .runTests();
    }

    @Test
    public void testRetrieveDateEndpoint() throws Exception {
        new DeviceStatusEndpointTestSpec<DeviceStatusDateResponse>() {

            @Override
            protected RestEndpoint<DeviceStatusRequest, DeviceStatusDateResponse> endpoint() {
                return client.retrieveDate;
            }

            @Override
            protected FraudPreventionDetectionScope getScope() {
                return RETRIEVE_SIM_SWAP_DATE;
            }
        }
        .runTests();
    }
    */
}
