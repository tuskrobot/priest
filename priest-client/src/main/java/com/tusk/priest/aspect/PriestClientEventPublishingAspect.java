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

package com.tusk.priest.aspect;

import com.tusk.priest.pojo.PriestRegistration;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 */
@Aspect
@Slf4j
@Component
public class PriestClientEventPublishingAspect {

	public static final String REGISTER_POINTCUT_EXPRESSION = "execution(* com.tusk.priest.service.PriestRegisterClientService.register(*)) && args(priestRegistration)";

	public static final String HEARTBEAT_POINTCUT_EXPRESSION = "execution(* com.tusk.priest.service.PriestRegisterClientService.heartbeat(*)) && args(priestRegistration)";

	public static final String DEREGISTER_POINTCUT_EXPRESSION = "execution(* com.tusk.priest.service.PriestRegisterClientService.deregister(*)) && args(priestRegistration)";


	@Before(value = REGISTER_POINTCUT_EXPRESSION)
	public void beforeRegister(PriestRegistration priestRegistration) {
		log.debug("[PRIEST CLIENT]===================>do registration, PriestRegistration" + priestRegistration);
	}

	@Before(value = DEREGISTER_POINTCUT_EXPRESSION)
	public void beforeDeregister(PriestRegistration priestRegistration) {
		log.debug("[PRIEST CLIENT]===================>do deregistration, PriestRegistration" + priestRegistration);
	}

	@Before(value = HEARTBEAT_POINTCUT_EXPRESSION)
	public void beforeHeartbeat(PriestRegistration priestRegistration) {
		log.debug("[PRIEST CLIENT]===================>do heartbeat, PriestRegistration" + priestRegistration);
	}

	@After(value = REGISTER_POINTCUT_EXPRESSION)
	public void afterRegister(PriestRegistration priestRegistration) {
		log.debug("[PRIEST CLIENT]===================>afterRegister");
	}

}
