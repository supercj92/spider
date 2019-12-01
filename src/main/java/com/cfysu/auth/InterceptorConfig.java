package com.cfysu.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2019/11/30.
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            //注册拦截器
            InterceptorRegistration iRegistration = registry.addInterceptor(new LoginInterceptor());
            iRegistration.addPathPatterns("/res/**");
        }
    }
