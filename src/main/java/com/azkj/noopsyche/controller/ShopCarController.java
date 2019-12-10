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
@Api(value = "生成购物车")
public class ShopCarController {
    @ApiOperation(value = "生成购物车",notes = "生成购物车",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/insertShopCar")
    public ApiResult insertShopCar(){
        ApiResult<Object> result = new ApiResult<>();
        return result;
    }
}
