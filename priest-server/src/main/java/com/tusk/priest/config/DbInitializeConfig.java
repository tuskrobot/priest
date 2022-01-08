package com.tusk.priest.config;

import com.tusk.priest.shiro.ShiroUtil;
import com.tusk.priest.modules.system.domain.User;
import com.tusk.priest.modules.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Date;

@Configuration
@Slf4j
public class DbInitializeConfig {

	@Autowired
	private UserService userService;

	@PostConstruct
	public void initialize(){
		try {
			User user = userService.getByUserName("tusk");
			if(null != user) return;

			user = new User();
			user.setUserName("tusk");
			user.setCreateDate(new Date());
			user.setUpdateDate(new Date());
			String salt = ShiroUtil.getRandomSalt();
			String encrypt = ShiroUtil.encrypt("tusk", salt);
			user.setPassword(encrypt);
			user.setSalt(salt);

			userService.save(user);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
	}
}
