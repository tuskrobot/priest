package com.tusk.priest.modules.system.service;


import com.tusk.priest.jpa.service.BaseService;
import com.tusk.priest.vo.ResultVo;
import com.tusk.priest.modules.system.domain.User;
import com.tusk.priest.modules.system.param.UserParam;

public interface UserService extends BaseService<User, Long> {
    ResultVo getPageList(UserParam param);

    User getByUserName(String userName);
}
