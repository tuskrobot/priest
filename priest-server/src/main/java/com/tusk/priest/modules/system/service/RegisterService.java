package com.tusk.priest.modules.system.service;


import com.tusk.priest.jpa.service.BaseService;
import com.tusk.priest.modules.system.domain.Register;
import com.tusk.priest.pojo.PriestDiscoveryProperties;
import com.tusk.priest.pojo.PriestRegistration;

import java.util.List;

public interface RegisterService extends BaseService<Register, String> {
    List<Register> getListGroupByType(String type);
    void register(PriestDiscoveryProperties properties);
    void heartbeat(PriestDiscoveryProperties properties);
}
