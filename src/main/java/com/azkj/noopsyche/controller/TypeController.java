package com.azkj.noopsyche.controller;


import com.azkj.noopsyche.service.TypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class TypeController {

    @Autowired
    private TypeService typeService;



    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public void search(Integer pageNumber,Integer pageSize,HttpServletRequest request, HttpServletResponse response) {
        String queryString = request.getParameter("queryString");
        // 若什么都不输入，则表示搜索全部商品
        queryString = StringUtils.isEmpty(queryString) ? "*" : queryString;
        String sort = request.getParameter("sort");
        if (StringUtils.isEmpty(queryString)) {
        }
        try {

            //Map<String, Object> maps = typeService.query(queryString, sort, pageNumber, pageSize);
          //  long count = (Long) maps.get("totals");
          } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
