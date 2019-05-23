package com.project.config.interceptor;

import com.project.model.Const;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if(uri.contains("js") ||uri.contains("login") ||uri.contains("css")||uri.contains("wx"))
        return true;
        else{
            Object user = request.getSession().getAttribute(Const.USER);
            if(user==null){
                response.sendRedirect("/guide/login");
                return false;
            }else {
                return true;
            }

        }
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}
