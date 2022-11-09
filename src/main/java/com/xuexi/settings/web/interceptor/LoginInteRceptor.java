package com.xuexi.settings.web.interceptor;

import com.xuexi.commons.contants.Contants;
import com.xuexi.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInteRceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //如果用户没有登录成功,则跳转到登录页面
        HttpSession session=httpServletRequest.getSession();
        User user=(User) session.getAttribute(Contants.SESSION_USER_KEY);
        if(user==null){
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath());//重定向时，url必须加项目的名称
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
