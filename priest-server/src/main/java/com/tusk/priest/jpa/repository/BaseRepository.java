package com.tusk.priest.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author alvin
 * @date 2018/8/14
 */
@NoRepositoryBean
public interface BaseRepository<E, ID extends Serializable> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E>, org.springframework.data.repository.Repository<E, ID>  {

}
