package com.eio.licenseserver.module.service;

import com.eio.licenseserver.module.domain.User;
import com.eio.licenseserver.common.jpa.service.BaseService;
import com.eio.licenseserver.module.param.UserParam;
import com.eio.licenseserver.module.vo.ResultVo;
import org.springframework.data.domain.Page;

public interface UserService extends BaseService<User, Long> {
    ResultVo getPageList(UserParam param);

    User getByUserName(String userName);
}
