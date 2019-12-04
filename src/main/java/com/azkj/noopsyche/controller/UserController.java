package com.azkj.noopsyche.controller;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.resp.ApiResult;

import com.azkj.noopsyche.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
@Slf4j
@Api(value = "用户")
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;
    @ApiOperation(value = "用户授权", notes = "用户授权", httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/user")
    public ApiResult user(String code, String uuid, String encryptedData, String iv) {
        ApiResult<Object> result = new ApiResult<>();
        try {
            String token=userService.login(code,uuid,encryptedData,iv);
        } catch (NoopsycheException e) {
            result.setMessage(e.getMessage());
            result.setCode(e.getStatusCode());
        }
        return result;
    }

}