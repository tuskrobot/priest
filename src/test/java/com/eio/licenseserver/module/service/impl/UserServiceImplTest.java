package com.eio.licenseserver.module.service.impl;

import com.eio.licenseserver.common.util.ShiroUtil;
import com.eio.licenseserver.module.domain.User;
import com.eio.licenseserver.module.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void getByUserName() {
        User user = userService.getByUserName("admin");
        System.out.println(user);
    }

    @Test()
    @Rollback(false)
    void save() {
        User user = new User();
        user.setUserName("admin");
        String salt = ShiroUtil.getRandomSalt();
        String encrypt = ShiroUtil.encrypt("123456", salt);
        user.setPassword(encrypt);
        user.setSalt(salt);
        userService.save(user);
    }
}