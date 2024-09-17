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

import com.vonage.client.DynamicEndpointTestSpec;
import com.vonage.client.VonageApiResponseException;
import com.vonage.client.auth.AuthMethod;
import com.vonage.client.auth.camara.NotApplicableScope;
import com.vonage.client.auth.camara.NetworkAuthMethod;
import com.vonage.client.camara.CamaraResponseException;
import com.vonage.client.common.HttpMethod;
import java.util.Set;

abstract class DeviceStatusEndpointTestSpec<R> extends DynamicEndpointTestSpec<DeviceStatusRequest, R> {
	protected String msisdn = "346661113334";

	@Override
	protected Set<Class<? extends AuthMethod>> expectedAuthMethods() {
		return Set.of(NetworkAuthMethod.class);
	}

	@Override
	protected Class<? extends VonageApiResponseException> expectedResponseExceptionType() {
		return CamaraResponseException.class;
	}

	@Override
	protected String expectedDefaultBaseUri() {
		return "https://api-eu.vonage.com";
	}

	@Override
	protected HttpMethod expectedHttpMethod() {
		return HttpMethod.POST;
	}

	@Override
	protected DeviceStatusRequest sampleRequest() {
		return new DeviceStatusRequest(msisdn);
	}

	@Override
	protected final String expectedEndpointUri(DeviceStatusRequest request) {
		return "/camara/device-status/v050/" + switch (getScope()) {
			case CONNECTIVITY_READ -> "connectivity";
			case ROAMING_READ -> "roaming";
		};
	}

	@Override
	protected String sampleRequestBodyString() {
		return "{\"device\":{\"phoneNumber\":\""+msisdn+"\"}}";
	}

	protected abstract NotApplicableScope getScope();
}
