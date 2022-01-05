package com.tusk.priest.server.modules.system.service;


import com.tusk.priest.server.jpa.service.BaseService;
import com.tusk.priest.server.vo.ResultVo;
import com.tusk.priest.server.modules.system.domain.User;
import com.tusk.priest.server.modules.system.param.UserParam;

public interface UserService extends BaseService<User, Long> {
    ResultVo getPageList(UserParam param);

    User getByUserName(String userName);
}
