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

import com.vonage.client.DynamicEndpointTestSpec;
import com.vonage.client.auth.AuthMethod;
import com.vonage.client.auth.TokenAuthMethod;
import com.vonage.client.common.HttpMethod;
import java.util.ArrayList;
import java.util.Collection;

abstract class AccountEndpointTestSpec<T, R> extends DynamicEndpointTestSpec<T, R> {

	@Override
	protected Collection<Class<? extends AuthMethod>> expectedAuthMethods() {
		Collection<Class<? extends AuthMethod>> authMethods = new ArrayList<>(2);
		authMethods.add(TokenAuthMethod.class);
		return authMethods;
	}

	@Override
	protected Class<? extends Exception> expectedResponseExceptionType() {
		return AccountResponseException.class;
	}

	@Override
	protected String expectedDefaultBaseUri() {
		return "https://rest.nexmo.com";
	}

	@Override
	protected HttpMethod expectedHttpMethod() {
		return HttpMethod.GET;
	}
}
