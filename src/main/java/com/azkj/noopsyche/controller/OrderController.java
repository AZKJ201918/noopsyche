package com.azkj.noopsyche.controller;

import com.azkj.noopsyche.common.resp.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@CrossOrigin
@Api(value = "订单模块")
public class OrderController {
    @ApiOperation(value = "生成订单",notes = "生成订单",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/createOrder")
    public ApiResult createOrder(){
        ApiResult<Object> result = new ApiResult<>();

        return result;
    }
}
