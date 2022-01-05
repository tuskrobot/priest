package com.tusk.priest.server.modules.system.service.impl;

import com.tusk.priest.server.modules.system.domain.Register;
import com.tusk.priest.server.modules.system.repository.RegisterRepository;
import com.tusk.priest.server.modules.system.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterRepository repository;

    @Override
    public RegisterRepository getRepository() {
        return repository;
    }

    @Override
    public List<Register> getListGroupByType(String type) {
        return repository.findByTypeOrderByCreateDateDesc(type);
    }

}
