package com.azkj.noopsyche.controller;

import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.resp.ApiResult;
import com.azkj.noopsyche.entity.Enter;
import com.azkj.noopsyche.service.EnterService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@Slf4j
public class EnterController {

    @Autowired
    @Qualifier("enterServiceImpl")
    private EnterService enterService;


    @ApiOperation(value = "商家入驻",notes = "商家入驻",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/merchantEnter")
    public ApiResult merchantEnter(Enter enter){
        ApiResult result=new ApiResult();
        try {
            enterService.merchantEnter(enter);
            result.setData("入驻成功");
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


    @ApiOperation(value = "查看审核结果",notes = "差看审核",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/Viewaudit")
    public ApiResult Viewaudit(Integer id){
        ApiResult result=new ApiResult();
        try {
            enterService.Viewaudit(id);
            result.setData("查看");
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
}
