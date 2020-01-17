package com.azkj.noopsyche.common.enm;

import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;

import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class CurrentUserHandlerMethodArgReslover implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //如果该参数注解有@CurrentUser且参数类型是User
        return methodParameter.getParameterAnnotation(UserToken.class) != null &&methodParameter.getParameterType() == String.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        //取得HttpServletRequest
        HttpServletRequest request= (HttpServletRequest) nativeWebRequest.getNativeRequest();
        //取出session中的User
        return (String)request.getHeader("token");
    }
}
