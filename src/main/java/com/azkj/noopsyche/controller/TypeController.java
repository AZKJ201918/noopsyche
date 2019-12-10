package com.azkj.noopsyche.controller;


import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.resp.ApiResult;
import com.azkj.noopsyche.service.TypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
public class TypeController {

    @Autowired
    private TypeService typeService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String tosearch() {
        return "jd";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ApiResult search(Integer pageNumber, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        ApiResult<Object> result = new ApiResult<>();
        String queryString = request.getParameter("queryString");
        // 若什么都不输入，则表示搜索全部商品
        queryString = StringUtils.isEmpty(queryString) ? "*" : queryString;
        String sort = request.getParameter("sort");
        if (StringUtils.isEmpty(queryString)) {
            result.setMessage("请求参数不能未空");
            result.setCode(Constants.RESP_STATUS_BADREQUEST);
            return result;
        }
        try {

            Map<String, Object> maps = typeService.query(queryString, sort, pageNumber, pageSize);
            long count = (Long) maps.get("totals");
            result.setMessage("查看成功");
            result.setData(maps);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }

}
