package com.azkj.noopsyche.config.datasource;


import com.azkj.noopsyche.common.intercepors.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {


    @Bean
    LoginInterceptor localInterceptor() {
        return new LoginInterceptor();
    }



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        WebMvcConfigurer.super.addInterceptors(registry);
        //registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/user");
        //registry.addInterceptor(localInterceptor()).addPathPatterns("/**").excludePathPatterns(/*"/uploading",*/"/user","/swagger-resources/**","/webjars/**", "/v2/**", "/swagger-ui.html/**");
    }
}