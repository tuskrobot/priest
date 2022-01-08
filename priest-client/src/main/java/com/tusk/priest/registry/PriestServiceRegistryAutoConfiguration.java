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

import com.tusk.priest.annotation.ConditionalOnPriestDiscoveryEnabled;
import com.tusk.priest.discovery.PriestDiscoveryAutoConfiguration;
import com.tusk.priest.pojo.PriestDiscoveryProperties;
import com.tusk.priest.pojo.PriestRegistration;
import com.tusk.priest.service.PriestRegisterClientService;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationAutoConfiguration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationConfiguration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luosu
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties
@ConditionalOnPriestDiscoveryEnabled
@ConditionalOnProperty(value = "spring.cloud.service-registry.auto-registration.enabled",
		matchIfMissing = true)
@AutoConfigureAfter({ AutoServiceRegistrationConfiguration.class,
		AutoServiceRegistrationAutoConfiguration.class,
		PriestDiscoveryAutoConfiguration.class })
public class PriestServiceRegistryAutoConfiguration {

	@Bean
	public PriestServiceRegistry priestServiceRegistry(PriestDiscoveryProperties priestDiscoveryProperties) {
		return new PriestServiceRegistry(priestDiscoveryProperties);
	}

	@Bean
	@ConditionalOnBean(AutoServiceRegistrationProperties.class)
	public PriestRegistration priestRegistration(PriestDiscoveryProperties priestDiscoveryProperties, ApplicationContext context) {
		return new PriestRegistration(priestDiscoveryProperties, context);
	}

}
