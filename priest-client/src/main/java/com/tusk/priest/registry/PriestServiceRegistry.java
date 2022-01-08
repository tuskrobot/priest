/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tusk.priest.registry;

import com.tusk.priest.exception.PriestException;
import com.tusk.priest.pojo.PriestDiscoveryProperties;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Properties;

import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

/**
 * @author luosu
 */
@Slf4j
public class PriestServiceRegistry {

	private static final String STATUS_UP = "UP";

	private static final String STATUS_DOWN = "DOWN";

	private final PriestDiscoveryProperties priestDiscoveryProperties;

	public PriestServiceRegistry(PriestDiscoveryProperties priestDiscoveryProperties) {
		this.priestDiscoveryProperties = priestDiscoveryProperties;
	}

	public void register(Registration registration) {

		if (StringUtils.isEmpty(registration.getServiceId())) {
			log.warn("No service to register for priest client...");
			return;
		}


	}

	public void deregister(Registration registration) {

		log.info("De-registering from Priest Server now...");

		if (StringUtils.isEmpty(registration.getServiceId())) {
			log.warn("No dom to de-register for priest client...");
			return;
		}


		log.info("De-registration finished.");
	}


}
