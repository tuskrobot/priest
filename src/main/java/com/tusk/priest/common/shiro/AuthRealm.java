package com.tusk.priest.common.shiro;

import com.tusk.priest.module.domain.User;
import com.tusk.priest.module.service.UserService;
import com.tusk.priest.common.util.ShiroUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;


/**
 * @author alvin
 * @date 2018/8/14
 */
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 1.3.1 授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {

        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 获取用户Principal对象
        User user = (User) principal.getPrimaryPrincipal();

        //添加资源的授权字符串
        //info.addStringPermission("user:add");

        //到数据库查询当前登陆用户的授权字符串
        /**
         * <!-演示状态-!>
         *     获取当前用户
         *     Subject subject = SecurityUtils.getSubject();
         *     User user = (User)subject.getPrincipal();
         *     User dbUser = userService.findById(id)
         *     info.addStringPermission(dbUser.getPerms());
         * </!-演示状态-!>
         */

        // 管理员拥有所有权限
        if(user.getId().equals(1L)){
            info.addStringPermission("*:*:*");
            return info;
        }


        return info;
    }

    /**
     * 1.3.2 认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 获取数据库中的用户名密码
        User user = userService.getByUserName(token.getUsername());
        // 判断用户名是否存在
        if(user == null){
            throw new UnknownAccountException();
        }else if(user.getStatus().equals(2)){
            throw new LockedAccountException();
        }
        // 对盐进行加密处理
        ByteSource salt = ByteSource.Util.bytes(user.getSalt());

        /* 传入密码自动判断是否正确
         * 参数1：传入对象给Principal
         * 参数2：正确的用户密码
         * 参数3：加盐处理
         * 参数4：固定写法
         */
        return new SimpleAuthenticationInfo(user, user.getPassword(), salt, getName());
    }

    /**
     * 自定义密码验证匹配器
     * credentials 证明
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        setCredentialsMatcher(new SimpleCredentialsMatcher() {
            @Override
            public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
                UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
                SimpleAuthenticationInfo info = (SimpleAuthenticationInfo) authenticationInfo;
                // 获取明文密码及密码盐
                String password = String.valueOf(token.getPassword());
                String salt = CodecSupport.toString(info.getCredentialsSalt().getBytes());

                return equals(ShiroUtil.encrypt(password, salt), info.getCredentials());
            }
        });
    }
}
