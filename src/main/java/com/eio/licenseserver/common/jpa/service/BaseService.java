package com.eio.licenseserver.common.jpa.service;

import com.eio.licenseserver.common.jpa.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author alvin
 * @date 2018/8/14
 */
@FunctionalInterface
public interface BaseService<E, ID extends Serializable> {

    BaseRepository<E, ID> getRepository();

    /**
     * 根据ID获取
     *
     * @param id
     * @return
     */
    default E get(ID id) {
        return getRepository().findById(id).orElse(null);
    }

    /**
     * 获取所有列表
     *
     * @return
     */
    default List<E> findAll() {
        return getRepository().findAll();
    }

    /**
     * 获取总数
     *
     * @return
     */
    default Long getTotalCount() {
        return getRepository().count();
    }

    /**
     * 保存
     *
     * @param entity
     * @return
     */
    default E save(E entity) {
        return getRepository().save(entity);
    }

    default Iterable<E> saveAll(Iterable<E> entities) {
        return getRepository().saveAll(entities);
    }

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    default E update(E entity) {
        return getRepository().saveAndFlush(entity);
    }

    /**
     * 批量保存与修改
     *
     * @param entities
     * @return
     */
    default Iterable<E> saveOrUpdateAll(Iterable<E> entities) {
        return getRepository().saveAll(entities);
    }

    /**
     * 删除
     *
     * @param entity
     */
    default void delete(E entity) {
        getRepository().delete(entity);
    }

    /**
     * 根据Id删除
     *
     * @param id
     */
    default void delete(ID id) {
        getRepository().deleteById(id);
    }

    /**
     * 批量删除
     *
     * @param entities
     */
    default void delete(Iterable<E> entities) {
        getRepository().deleteAll(entities);
    }

    /**
     * 清空缓存，提交持久化
     */
    default void flush() {
        getRepository().flush();
    }

    /**
     * 根据条件查询获取
     *
     * @param spec
     * @return
     */
    default List<E> findAll(Specification<E> spec) {
        return getRepository().findAll(spec);
    }

    /**
     * 分页获取
     *
     * @param pageable
     * @return
     */
    default Page<E> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    /**
     * 根据查询条件分页获取
     *
     * @param spec
     * @param pageable
     * @return
     */
    default Page<E> findAll(Specification<E> spec, Pageable pageable) {
        return getRepository().findAll(spec, pageable);
    }

    /**
     * 获取查询条件的结果数
     *
     * @param spec
     * @return
     */
    default long count(Specification<E> spec) {
        return getRepository().count(spec);
    }

    /**
     * 获取以主键以及实体类作为key/value的map
     * 实体类必须要有@id注解
     */
    @SuppressWarnings("unchecked")
    default Map<ID, E> getEntityMap() {
        Map<ID, E> map = new HashMap<>();
        List<E> list = getRepository().findAll();
        list.forEach(entity -> {
            Field[] fields = entity.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field.setAccessible(fields, true);
                if (fields[i].isAnnotationPresent(Id.class)) {
                    try {
                        map.put((ID) fields[i].get(entity), entity);
                        break;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return map;
    }

    /**
     * 获取以keyName/valueName作为key/value的map
     */
    default Map<String, String> getMapByKey(String keyName, String valueName) {
        Map<String, String> map = new HashMap<>();
        List<E> list = getRepository().findAll();
        list.forEach(entity -> {
            Field[] fields = entity.getClass().getDeclaredFields();
            String key = "";
            String value = "";
            for (int i = 0; i < fields.length; i++) {
                Field.setAccessible(fields, true);
                if (fields[i].getName().equalsIgnoreCase(keyName)) {
                    try {
                        key = (String)fields[i].get(entity);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else if (fields[i].getName().equalsIgnoreCase(valueName)) {
                    try {
                        value = (String)fields[i].get(entity);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            map.put(key, value);
        });

        return map;
    }



}