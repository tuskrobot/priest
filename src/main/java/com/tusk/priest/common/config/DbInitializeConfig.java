package com.tusk.priest.common.config;

import com.tusk.priest.module.domain.User;
import com.tusk.priest.module.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
public class DbInitializeConfig {

	@Autowired
	private UserService userService;

	@PostConstruct
	public void initialize(){
		try {
			User user = new User("tusk", "tusk");
			userService.save(user);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
	}
}
