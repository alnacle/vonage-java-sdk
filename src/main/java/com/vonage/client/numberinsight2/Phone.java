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
package com.vonage.client.numberinsight2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vonage.client.JsonableBaseObject;

/**
 * Represents details of the phone number in {@link FraudCheckResponse#getPhone()}.
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Phone extends JsonableBaseObject {
	private String number, carrier;
	private PhoneType type;

	protected Phone() {}

	/**
	 * Phone number used in the fraud check operation(s).
	 * 
	 * @return The phone number in E.164 format.
	 */
	@JsonProperty("phone")
	public String getNumber() {
		return number;
	}

	/**
	 * Name of the network carrier, if {@linkplain Insight#FRAUD_SCORE} was requested.
	 * 
	 * @return The network carrier name, or {@code null} if not applicable.
	 */
	@JsonProperty("carrier")
	public String getCarrier() {
		return carrier;
	}

	/**
	 * Type of phone, if {@linkplain Insight#FRAUD_SCORE} was requested.
	 * 
	 * @return The phone type as an enum, or {@code null} if not applicable.
	 */
	@JsonProperty("type")
	public PhoneType getType() {
		return type;
	}
}
