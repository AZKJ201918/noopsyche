package com.azkj.noopsyche.controller;



import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.resp.ApiResult;
import com.azkj.noopsyche.entity.Mini;
import com.azkj.noopsyche.entity.MiniOrder;
import com.azkj.noopsyche.service.MiniService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@CrossOrigin
public class MiniController {

    @Autowired
    @Qualifier("miniServiceImpl")
    private MiniService miniService;


    @ApiOperation(value = "查看小程序",notes = "查看小程序",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/selectmini")
    public ApiResult SelectMini() {
        ApiResult<List<Mini>> result=new ApiResult();
        try {
            List<Mini> miniList=miniService.SelectMini();
            result.setData(miniList);
            result.setMessage("查看成功");
        } catch (NoopsycheException e) {
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }


    @ApiOperation(value = "查看小程序",notes = "查看小程序",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/minidetails")
    public ApiResult minidetails(Integer id) {
        ApiResult<Mini> result=new ApiResult();
        try {
            Mini mini=miniService.Minidetails(id);
            result.setData(mini);
            result.setMessage("查看成功");
        } catch (NoopsycheException e) {
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }


    @ApiOperation(value = "购买小程序",notes = "购买小程序",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/purchaseMini")
    public ApiResult purchaseMini(@RequestBody MiniOrder miniOrder) {
        ApiResult<Map<String,String>> result=new ApiResult();
        try {
            Map<String,String> map=miniService.purchaseMini(miniOrder);
            result.setData(map);
            result.setMessage("小程序订单成功");
        } catch (NoopsycheException e) {
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }




    @ApiOperation(value = "小程序回调",notes = "小程序回调",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/mininotifyurl")
    public Map purchaseMini(HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> map=new HashMap<>();
        String inputLine = "";
        String notityXml = "";
        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notityXml += inputLine;
            }
            request.getReader().close();
            miniService.mininotifyurl(notityXml);
            map.put("return_code","01");
            map.put("return_msg","回调成功");
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);

        }
        return map;
    }


    @ApiOperation(value = "测试",notes = "测试",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/testpay")
    public ApiResult<Map<String,String>> test(String token,String price) {
        ApiResult<Map<String,String>> result=new ApiResult();
        try {
            Map<String,String> map=miniService.testpay(token, Long.valueOf(price));
            result.setData(map);
            result.setMessage("测试");
        } catch (NoopsycheException e) {
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }


}


