package com.eio.licenseserver.module.service.impl;

import com.eio.licenseserver.common.data.PageSort;
import com.eio.licenseserver.common.enums.LicenseStatusEnum;
import com.eio.licenseserver.common.exception.ResultException;
import com.eio.licenseserver.common.util.ResultVoUtil;
import com.eio.licenseserver.common.util.StringUtil;
import com.eio.licenseserver.common.util.encrypt.LICENSEEncrypt;
import com.eio.licenseserver.common.util.encrypt.RSAEncrypt;
import com.eio.licenseserver.module.domain.License;
import com.eio.licenseserver.module.param.LicenseParam;
import com.eio.licenseserver.module.repository.LicenseRepository;
import com.eio.licenseserver.module.service.LicenseService;
import com.eio.licenseserver.module.vo.LicenseVo;
import com.eio.licenseserver.module.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class LicenseServiceImpl implements LicenseService {

    @Autowired
    private LicenseRepository repository;

    @Override
    public LicenseRepository getRepository() {
        return repository;
    }

    @Override
    public ResultVo getPageList(LicenseParam param) {
        try{
            PageRequest page = PageSort.pageRequest4Post(param.getPage(), param.getLimit(),"id", Sort.Direction.ASC);
            Page<License> licensesPage = repository.findAll(new Specification<License>() {
                @Override
                public Predicate toPredicate(Root<License> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> preList = new ArrayList<>();

                    if(null != param.getParam()){

                        if(null != param.getParam().getId()){
                            preList.add(cb.equal(root.get("id").as(Integer.class), param.getParam().getId()));
                        }

                        if(StringUtil.isNotBlank(param.getParam().getProjectName())){
                            preList.add(cb.equal(root.get("projectName").as(String.class), param.getParam().getProjectName()));
                        }

                        if(null != param.getParam().getRegisterDate()){
                            preList.add(cb.greaterThanOrEqualTo(root.get("registerDate").as(Date.class),  param.getParam().getRegisterDate()));
                        }

                        if(null != param.getParam().getExpireDate()){
                            preList.add(cb.lessThanOrEqualTo(root.get("expireDate").as(Date.class), param.getParam().getExpireDate()));
                        }
                    }

                    Predicate[] pres = new Predicate[preList.size()];
                    return query.where(preList.toArray(pres)).getRestriction();
                }
            }, page);

            List<LicenseVo> licenseVoList = new ArrayList<>();
            List<License> licenseList = licensesPage.getContent();
            licenseList.forEach(item ->{
                LicenseVo licenseVo = new LicenseVo(item);
                licenseVoList.add(licenseVo);
            });

            PageImpl pageImpl = new PageImpl<>(licenseVoList, page, licensesPage.getTotalElements());
            return ResultVoUtil.success(pageImpl.getContent(), pageImpl.getTotalElements());

        }catch (ResultException e){
            e.printStackTrace();
            return ResultVoUtil.error("查询数据失败");
        }
    }

    @Override
    public List<String> getProjectNames() {
        List<String> projectNames = new ArrayList<>();
        repository.findAll().stream().forEach(license -> projectNames.add(license.getProjectName()));
        return projectNames;
    }

    @Override
    public ResultVo save(LicenseParam.BaseParam param) {
        try {
            if(null != repository.findByProjectName(param.getProjectName())){
                return ResultVoUtil.error("项目名称已存在");
            }else {
                License license = new License(param);
                repository.save(license);
                return ResultVoUtil.success("保存成功");
            }
        }catch (ResultException e){
            e.printStackTrace();
            return ResultVoUtil.error("保存失败");
        }

    }

    @Override
    public ResultVo genRegister(Long id) throws Exception {
        try {
            License license =  repository.getOne(id);

            /**
             * 加密
             * keyPair 0 公钥  1 私钥
             */
            Map<Integer, String> keyPair = RSAEncrypt.genKeyPair();

            /**
             * 解密
             */
            String tokenPlaintext = LICENSEEncrypt.decrypt(license.getToken());

            /**
             * 加密
             */
            String  register = RSAEncrypt.encrypt(tokenPlaintext + "_" + license.getExpireDate(), keyPair.get(0));
            String registerCipherText = LICENSEEncrypt.encrypt(register, keyPair.get(1));
            license.setRegister(registerCipherText);
            license.setStatus(LicenseStatusEnum.REGISTERED.getCode());
            repository.save(license);
            System.out.println("register 明文：" + tokenPlaintext + "_" + license.getExpireDate());
            System.out.println("register 私钥：" + keyPair.get(1));
            System.out.println("registerCipherText：" + registerCipherText);
            return ResultVoUtil.success("授权成功");
        }catch (ResultException | SocketException | NoSuchAlgorithmException e){
            e.printStackTrace();
            return ResultVoUtil.error("授权失败");
        }

    }

    @Override
    public ResultVo update(LicenseParam.BaseParam param) {
       try {
           License license = repository.getOne(param.getId());
           license.setProjectName(param.getProjectName());
           license.setExpireDate(param.getExpireDate());
           license.setToken(param.getToken());
           repository.save(license);
           return ResultVoUtil.success("更新成功");
       }catch (ResultException e){
           e.printStackTrace();
           return ResultVoUtil.error("更新失败");
       }

    }


    @Override
    public LicenseVo edit(Long id) {
        return  new LicenseVo(repository.getOne(id));
    }

    @Override
    public ResultVo disable(Long id) {
        try {
            License license = repository.getOne(id);
            license.setStatus(LicenseStatusEnum.DISABLED.getCode());
            repository.save(license);
            return ResultVoUtil.success("禁用成功");
        }catch (ResultException e){
            e.printStackTrace();
            return ResultVoUtil.error("禁用失败");
        }

    }

}
