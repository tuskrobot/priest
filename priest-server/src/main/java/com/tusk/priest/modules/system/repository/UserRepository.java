package com.tusk.priest.modules.system.repository;

import com.tusk.priest.jpa.repository.BaseRepository;
import com.tusk.priest.modules.system.domain.User;
import org.springframework.stereotype.Repository;

/**
 * @author alvin
 * @date 2018/12/02
 */
@Repository
public interface UserRepository extends BaseRepository<User,Long> {
    User getByUserName(String userName);
}

