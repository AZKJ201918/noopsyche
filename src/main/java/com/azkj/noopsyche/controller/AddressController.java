package com.azkj.noopsyche.controller;

import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.resp.ApiResult;
import com.azkj.noopsyche.entity.Address;
import com.azkj.noopsyche.service.AddressService;
import com.github.pagehelper.PageInfo;
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
@Api(value = "地址模块")//地址模块测试完毕
public class AddressController {
    @Autowired
    @Qualifier("addressServiceImpl")
    private AddressService addressService;
    @ApiOperation(value = "新增地址", notes = "新增地址", httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/insertAddress")
    public ApiResult insertAddress(Address address){
        ApiResult<Object> result = new ApiResult<>();
        try {
            addressService.addAddress(address);
            result.setMessage("地址新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "修改地址", notes = "修改地址", httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/updateAddress")
    public ApiResult updateAddress(Address address){
        ApiResult<Object> result = new ApiResult<>();
        try {
            addressService.modifyAddress(address);
            result.setMessage("地址修改成功");
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "查看地址", notes = "查看地址", httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/loadAddress")
    public ApiResult loadAddress(Integer page,Integer limit,String token){
        ApiResult<Object> result = new ApiResult<>();
        try {
            PageInfo<Address> pageInfo=addressService.findAddress(page,limit,token);
            result.setMessage("查看地址成功");
            result.setData(pageInfo);
        } catch (NoopsycheException e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setCode(e.getStatusCode());
        }catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "删除地址", notes = "删除地址", httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/deleteAddress")
    public ApiResult deleteAddress(Integer id){
        ApiResult<Object> result = new ApiResult<>();
        try {
            addressService.removeAddress(id);
            result.setMessage("地址删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
}
