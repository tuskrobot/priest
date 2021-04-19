package com.eio.licenseserver.module.service.impl;

import com.eio.licenseserver.common.util.encrypt.RSAEncrypt;
import com.eio.licenseserver.module.domain.License;
import com.eio.licenseserver.module.service.LicenseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class LicenseServiceImplTest {

    @Autowired
    private LicenseService licenseService;

    @Test
    public void decrypt() throws Exception {
        License license =  licenseService.get(29L);
        String token = RSAEncrypt.decrypt(license.getToken(), license.getRemark());
        System.out.println("RSA解密 :" + token);
    }
}
