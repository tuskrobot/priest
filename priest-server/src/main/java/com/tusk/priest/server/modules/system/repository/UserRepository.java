package com.tusk.priest.server.modules.system.repository;

import com.tusk.priest.server.jpa.repository.BaseRepository;
import com.tusk.priest.server.modules.system.domain.User;
import org.springframework.stereotype.Repository;

/**
 * @author alvin
 * @date 2018/12/02
 */
@Repository
public interface UserRepository extends BaseRepository<User,Long> {
    User getByUserName(String userName);
}

