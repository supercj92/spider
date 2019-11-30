package com.cfysu.auth;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求的地址（根域名以外的部分）
        String uri = request.getRequestURI();
        if (uri.indexOf("/login") >= 0){
            return true;
        }
        //获取session，有就是说明已经登录，没有就是拦截访问并跳转到登录页面
        HttpSession session = request.getSession();
        String token = (String) session.getAttribute("token");
        if (token != null){
            return true;
        }
        request.setAttribute("msg","input token");
//        request.getRequestDispatcher("/WEB-INF/jsp/userlogin.jsp").forward(request,response);
        return false;
    }
}
