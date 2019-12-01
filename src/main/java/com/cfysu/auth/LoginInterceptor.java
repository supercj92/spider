package com.cfysu.auth;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    public static String globalSwitch = "";

    public static final String SWITCH_CLOSE = "CLOSE";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求的地址（根域名以外的部分）
        String uri = request.getRequestURI();
        if (uri.contains("/login")){
            return true;
        }
        //获取session，有就是说明已经登录，没有就是拦截访问并跳转到登录页面
        HttpSession session = request.getSession();
        String token = (String) session.getAttribute("token");
        if (token != null){
            if(uri.contains("/open")){
                return true;
            }
            if(SWITCH_CLOSE.equals(globalSwitch)){
                return false;
            }
            return true;
        }
        request.setAttribute("msg","input token");
//        request.getRequestDispatcher("/WEB-INF/jsp/userlogin.jsp").forward(request,response);
        return false;
    }

    public String getGlobalSwitch() {
        return globalSwitch;
    }

    public void setGlobalSwitch(String globalSwitch) {
        this.globalSwitch = globalSwitch;
    }
}
