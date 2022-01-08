package com.tusk.priest.listener;

import com.tusk.priest.event.PriestClientRegisterEvent;
import com.tusk.priest.pojo.PriestRegistration;
import org.springframework.context.ApplicationListener;

public class PriestClientRegisterListener implements ApplicationListener<PriestClientRegisterEvent> {

    @Override
    public void onApplicationEvent(PriestClientRegisterEvent event) {
        PriestRegistration registration = event.getPriestRegistration();

        System.out.println("注册服务:" + registration.getServiceId() + " HOST:" + registration.getHost() + "");
    }
}
