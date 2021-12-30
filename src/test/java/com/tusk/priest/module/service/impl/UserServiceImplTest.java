package com.tusk.priest.module.service.impl;

import com.tusk.priest.common.util.ShiroUtil;
import com.tusk.priest.module.domain.User;
import com.tusk.priest.module.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Slf4j
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
//        userService.save(user);
    }
}
