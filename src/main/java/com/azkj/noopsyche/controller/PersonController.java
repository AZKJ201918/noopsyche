package com.azkj.noopsyche.controller;

import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.resp.ApiResult;
import com.azkj.noopsyche.entity.Person;
import com.azkj.noopsyche.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin
@Api(value = "个人页面模块")
public class PersonController {
    @Autowired
    private PersonService personService;

    @ApiOperation(value = "新增个人页面",notes = "新增个人页面",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/insertMyViem")
    public ApiResult insertMyViem(@RequestBody Person person){
        ApiResult result = new ApiResult<>();
        try {
            personService.addPerson(person);
            result.setMessage("新增页面成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "查看个人页面",notes = "查看个人页面",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("/loadMyView")
    public ApiResult loadMyView(){
        ApiResult<Person> result = new ApiResult<>();
        try {
            Person person=personService.findMyView();
            result.setMessage("查看个人页面成功");
            result.setData(person);
        }catch (NoopsycheException e){
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setCode(e.getStatusCode());
        }catch (Exception e){
            e.printStackTrace();
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
}
