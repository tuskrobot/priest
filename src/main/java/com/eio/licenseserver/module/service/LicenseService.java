package com.eio.licenseserver.module.service;

import com.eio.licenseserver.common.jpa.service.BaseService;
import com.eio.licenseserver.module.domain.License;
import com.eio.licenseserver.module.param.LicenseParam;
import com.eio.licenseserver.module.vo.LicenseVo;
import com.eio.licenseserver.module.vo.ResultVo;

import java.util.List;

public interface LicenseService extends BaseService<License, Long> {
    ResultVo getPageList(LicenseParam param);

    List<String> getProjectNames();

    ResultVo save(LicenseParam.BaseParam param);

    ResultVo genRegister(Long id) throws Exception;

    ResultVo update(LicenseParam.BaseParam param);

    LicenseVo edit(Long id);

    ResultVo disable(Long id);
}
