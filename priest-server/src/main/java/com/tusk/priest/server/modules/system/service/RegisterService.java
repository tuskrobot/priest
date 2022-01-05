package com.tusk.priest.server.modules.system.service;


import com.tusk.priest.server.jpa.service.BaseService;
import com.tusk.priest.server.modules.system.domain.Register;
import com.tusk.priest.server.vo.ResultVo;

import java.util.List;

public interface RegisterService extends BaseService<Register, String> {
    List<Register> getListGroupByType(String type);
}
