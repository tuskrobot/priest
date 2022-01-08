package com.tusk.priest.modules.system.service.impl;

import com.tusk.priest.event.PriestClientRegisterEvent;
import com.tusk.priest.event.PriestServerRegisterEvent;
import com.tusk.priest.modules.system.domain.Register;
import com.tusk.priest.modules.system.repository.RegisterRepository;
import com.tusk.priest.modules.system.service.RegisterService;
import com.tusk.priest.pojo.PriestDiscoveryProperties;
import com.tusk.priest.pojo.PriestRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterRepository repository;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public RegisterRepository getRepository() {
        return repository;
    }

    @Override
    public List<Register> getListGroupByType(String type) {
        return repository.findByTypeOrderByCreateDateDesc(type);
    }

    @Override
    public void register(PriestDiscoveryProperties properties) {
        repository.save(new Register(properties));

//        applicationContext.publishEvent(new PriestServerRegisterEvent(this, properties));
    }

    @Override
    public void heartbeat(PriestDiscoveryProperties properties) {

    }
}
