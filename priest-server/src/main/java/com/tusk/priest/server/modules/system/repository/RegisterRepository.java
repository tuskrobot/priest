package com.tusk.priest.server.modules.system.repository;

import com.tusk.priest.server.jpa.repository.BaseRepository;
import com.tusk.priest.server.modules.system.domain.Register;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author alvin
 * @date 2018/12/02
 */
@Repository
public interface RegisterRepository extends BaseRepository<Register, String> {
    List<Register> findByTypeOrderByCreateDateDesc(String type);
}

