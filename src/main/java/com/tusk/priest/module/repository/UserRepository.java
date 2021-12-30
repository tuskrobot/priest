package com.tusk.priest.module.repository;

import com.tusk.priest.common.jpa.repository.BaseRepository;
import com.tusk.priest.module.domain.User;
import org.springframework.stereotype.Repository;

/**
 * @author alvin
 * @date 2018/12/02
 */
@Repository
public interface UserRepository extends BaseRepository<User,Long> {
    User getByUserName(String userName);
}

