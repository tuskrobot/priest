package com.eio.licenseserver.module.service.impl;

import com.eio.licenseserver.common.data.PageSort;
import com.eio.licenseserver.common.exception.ResultException;
import com.eio.licenseserver.common.util.ResultVoUtil;
import com.eio.licenseserver.common.util.StringUtil;
import com.eio.licenseserver.module.domain.User;
import com.eio.licenseserver.module.param.UserParam;
import com.eio.licenseserver.module.repository.UserRepository;
import com.eio.licenseserver.module.service.UserService;
import com.eio.licenseserver.module.vo.ResultVo;
import com.eio.licenseserver.module.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserRepository getRepository() {
        return repository;
    }

    @Override
    public ResultVo getPageList(UserParam param) {
        try{
            PageRequest page = PageSort.pageRequest4Post(param.getPage(), param.getLimit(),"id", Sort.Direction.ASC);
            Page<User> licensesPage = repository.findAll(new Specification<User>() {
                @Override
                public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> preList = new ArrayList<>();

                    if(StringUtil.isNotBlank(param.getUserName())){
                        preList.add(cb.lessThanOrEqualTo(root.get("userName").as(String.class), param.getUserName()));
                    }

                    Predicate[] pres = new Predicate[preList.size()];
                    return query.where(preList.toArray(pres)).getRestriction();
                }
            }, page);

            List<UserVo> userVoList = new ArrayList<>();
            List<User> userList = licensesPage.getContent();
            userList.forEach(item ->{
                UserVo licenseVo = new UserVo(item);
                userVoList.add(licenseVo);
            });

            PageImpl pageImpl = new PageImpl<>(userVoList, page, licensesPage.getTotalElements());
            return ResultVoUtil.success(pageImpl.getContent(), pageImpl.getTotalElements());

        }catch (ResultException e){
            return ResultVoUtil.error("查询数据失败");
        }
    }

    @Override
    public User getByUserName(String userName) {
        return repository.getByUserName(userName);
    }
}
