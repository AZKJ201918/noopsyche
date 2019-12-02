package com.azkj.noopsyche.controller;

import com.azkj.noopsyche.common.resp.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Api(value = "视频")
public class VoidController {


    @ApiOperation(value = "品牌视频",notes = "品牌视频",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/video")
    public ApiResult SelectVoideo(){
        ApiResult result=new ApiResult();


        return result;
    }
}
