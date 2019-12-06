package com.azkj.noopsyche.common.intercepors;


import com.alibaba.fastjson.JSON;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.utils.UserElementUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

//@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private  UserElementUtils userElementUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String uri = request.getRequestURI();
        String token = request.getHeader("token");
        Map<String,Object> map=new HashMap<>();
        try {
            Boolean userElement=userElementUtils.UserElement(token);
            return userElement;
        } catch (NoopsycheException e) {
            map.put("code",e.getStatusCode());
            map.put("message",e.getMessage());
            returnJson(response, JSON.toJSONString(map).toString());
            return false;
        }catch (Exception e){
            map.put("code",500);
            map.put("message","内部错误");
            returnJson(response, JSON.toJSONString(map).toString());
            return false;
        }
    }





    private void returnJson(HttpServletResponse response, String json) throws Exception{
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("appliction/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
           log .error("response error",e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
