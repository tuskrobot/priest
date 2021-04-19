package com.eio.licenseserver.module.repository;

import com.eio.licenseserver.module.domain.User;
import com.eio.licenseserver.common.jpa.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * @author alvin
 * @date 2018/12/02
 */
@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    User getByUserName(String userName);
}

