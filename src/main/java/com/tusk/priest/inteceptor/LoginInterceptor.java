package com.tusk.priest.inteceptor;

import com.tusk.priest.modules.system.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author teafr
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter  {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        //未登录去登录
        if(null == user){
            response.sendRedirect("/login");
        }
        return true;
    }
}
