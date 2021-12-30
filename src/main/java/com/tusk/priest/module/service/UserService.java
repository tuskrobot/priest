package com.tusk.priest.module.service;

import com.tusk.priest.module.domain.User;
import com.tusk.priest.common.jpa.service.BaseService;
import com.tusk.priest.module.param.UserParam;
import com.tusk.priest.module.vo.ResultVo;

public interface UserService extends BaseService<User, Long> {
    ResultVo getPageList(UserParam param);

    User getByUserName(String userName);
}
