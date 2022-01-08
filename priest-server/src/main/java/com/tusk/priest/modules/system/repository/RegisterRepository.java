package com.tusk.priest.modules.system.repository;

import com.tusk.priest.jpa.repository.BaseRepository;
import com.tusk.priest.modules.system.domain.Register;
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

