package com.eio.licenseserver.module.repository;

import com.eio.licenseserver.common.jpa.repository.BaseRepository;
import com.eio.licenseserver.module.domain.License;
import com.eio.licenseserver.module.domain.User;
import org.springframework.stereotype.Repository;

/**
 * @author alvin
 * @date 2018/12/02
 */
@Repository
public interface LicenseRepository extends BaseRepository<License, Long> {

    License findByProjectName(String projectName);
}

