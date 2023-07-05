/*
 *   Copyright 2023 Vonage
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
package com.vonage.client.subaccounts;

import com.vonage.client.AbstractMethod;
import com.vonage.client.HttpWrapper;
import com.vonage.client.auth.TokenAuthMethod;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.methods.RequestBuilder;
import java.io.IOException;

class CreateSubaccountEndpoint extends AbstractMethod<CreateSubaccountRequest, Account> {
	private static final Class<?>[] ALLOWED_AUTH_METHODS = {TokenAuthMethod.class};
	private static final String PATH = "/accounts/%s/subaccounts";

	CreateSubaccountEndpoint(HttpWrapper httpWrapper) {
		super(httpWrapper);
	}

	@Override
	protected Class<?>[] getAcceptableAuthMethods() {
		return ALLOWED_AUTH_METHODS;
	}

	@Override
	public RequestBuilder makeRequest(CreateSubaccountRequest request) {
		String path = String.format(PATH, request.primaryAccountApiKey = getApplicationIdOrApiKey());
		String uri = httpWrapper.getHttpConfig().getApiBaseUri() + path;
		return RequestBuilder.post(uri)
				.setHeader("Content-Type", "application/json")
				.setHeader("Accept", "application/json")
				.setEntity(new StringEntity(request.toJson(), ContentType.APPLICATION_JSON));
	}

	@Override
	public Account parseResponse(HttpResponse response) throws IOException {
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode >= 200 && statusCode < 300) {
			return Account.fromJson(basicResponseHandler.handleResponse(response));
		}
		else {
			throw SubaccountsResponseException.fromHttpResponse(response);
		}
	}
}