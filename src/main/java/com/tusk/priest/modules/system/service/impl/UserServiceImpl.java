package com.tusk.priest.modules.system.service.impl;

import com.tusk.priest.exception.ResultException;
import com.tusk.priest.util.ResultVoUtil;
import com.tusk.priest.modules.system.domain.User;
import com.tusk.priest.modules.system.param.UserParam;
import com.tusk.priest.modules.system.repository.UserRepository;
import com.tusk.priest.modules.system.service.UserService;
import com.tusk.priest.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
/*
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
*/
            return ResultVoUtil.SUCCESS;
        }catch (ResultException e){
            return ResultVoUtil.error("查询数据失败");
        }
    }

    @Override
    public User getByUserName(String userName) {
        return repository.getByUserName(userName);
    }
}
