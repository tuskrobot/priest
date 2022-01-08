package com.tusk.priest.service;

import com.tusk.priest.pojo.PriestRegistration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PriestRegisterClientService {

    @Autowired
    private ApplicationContext applicationContext;

    public void register(PriestRegistration registration) {
//        applicationContext.publishEvent(new PriestClientRegisterEvent(this, registration));

    }

    public void deregister(PriestRegistration registration) {

//        applicationContext.publishEvent(new PriestClientRegisterEvent(this, registration));
    }

    public void heartbeat() throws Exception {
        log.debug("heartbeat.....");
    }
}
