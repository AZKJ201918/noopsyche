package com.azkj.noopsyche.controller;

import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.resp.ApiResult;

import com.azkj.noopsyche.common.utils.DateUtil;
import com.azkj.noopsyche.common.utils.RedisUtil;
import com.azkj.noopsyche.common.utils.sm.sendSmsUtil;
import com.azkj.noopsyche.entity.*;
import com.azkj.noopsyche.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.azkj.noopsyche.common.constants.Constants.token;


@RestController
@CrossOrigin
@Slf4j
@Api(value = "用户")
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;



  /*  @ApiOperation(value = "用户授权", notes = "用户登录", httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/user")
    public ApiResult user(WxUser wxUser) {
        ApiResult<Object> result = new ApiResult<>();
        try {
            String token=userService.login(wxUser);
            result.setData(token);
            result.setMessage("登录成功");
        } catch (NoopsycheException e) {
            result.setMessage(e.getMessage());
            result.setCode(e.getStatusCode());
        }catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }*/
    @ApiOperation(value = "登录", notes = "登录", httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/login")
    public ApiResult login(String code, String encryptedData, String iv,String uuid) {
        ApiResult<WxUser> result = new ApiResult<>();
        try {
            WxUser user = userService.encode(code, encryptedData, iv, uuid);
            result.setData(user);
            result.setMessage("登录成功");
        } catch (NoopsycheException e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setCode(e.getStatusCode());
        }catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "获取验证码",notes = "获取验证码",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/getSmsCode")
    public ApiResult getSmsCode(String phone){
        ApiResult<Object> result = new ApiResult<>();
        try {
            userService.sendSmsCode(phone);
            result.setMessage("验证码发送正常");
        } catch (NoopsycheException e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setCode(e.getStatusCode());
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage("服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "是否注册",notes = "是否注册",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/isRegister")
    public ApiResult isRegister(String token){
        ApiResult<Object> result = new ApiResult<>();
        try {
            userService.findRegister(token);
            result.setMessage("用户已注册");
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
    @ApiOperation(value = "注册",notes = "注册",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/register")//已测试
    public ApiResult register(@RequestBody Register register,String smsCode){
        ApiResult<String> result = new ApiResult<>();
        try {
            userService.addRegister(register,smsCode);
            result.setMessage("注册成功");
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "修改手机号",notes = "修改手机号,通过token",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/modifyPhone")
    public ApiResult modifyPhone(@RequestBody Register register){//已测试
        ApiResult<String> result = new ApiResult<>();
        try {
            userService.modifyPhone(register);
            result.setMessage("修改手机号成功");
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
    @ApiOperation(value = "新增银行卡",notes = "新增银行卡",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/addBank")
    public ApiResult addBank(@RequestBody Bank bank){//已测试
        ApiResult<String> result = new ApiResult<>();
        try {
            userService.addBank(bank);
            result.setMessage("新增银行卡成功");
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "查看银行卡",notes = "查看银行卡",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/selectBank")
    public ApiResult selectBank(String token){
        ApiResult<List<Bank>> result = new ApiResult<>();
        try {
            List<Bank> bankList=userService.findBank(token);
            result.setMessage("查看银行卡成功");
            result.setData(bankList);
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
    @ApiOperation(value = "删除银行卡",notes = "根据银行卡号删除银行卡",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/deleteBank")
    public ApiResult deleteBank(@RequestBody Bank bank){//已删除
        ApiResult<String> result = new ApiResult<>();
        try {
            userService.removeBank(bank);
            result.setMessage("删除银行卡成功");
        }catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "修改为默认银行卡",notes = "需要用户token",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/modifyBank")
    public ApiResult modifyBank(@RequestBody Bank bank){//已测试
        ApiResult<String> result = new ApiResult<>();
        try {
            userService.modifyBank(bank);
            result.setMessage("修改为默认银行卡成功");
        }catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "积分兑换臻豆",notes = "积分兑换臻豆",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/changeBean")
    public ApiResult changeBean(Integer score,String uuid){
        ApiResult<String> result = new ApiResult<>();
        try {
            userService.exchangePea(score,uuid);
        }catch (NoopsycheException e) {
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
    @ApiOperation(value = "查看所有使用中的新人优惠劵",notes = "查看新人优惠劵",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/loadNewCoupon")
    public ApiResult loadNewCoupon(){
        ApiResult<Object> result = null;
        try {
            result = new ApiResult<>();
            List<Coupon> couponList=userService.findAllNewCoupon();
            result.setMessage("查看新人优惠劵成功");
            result.setData(couponList);
        } catch (NoopsycheException e) {
            e.printStackTrace();
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "领取新人优惠劵",notes = "领取新人优惠劵",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/getNewCoupon")
    public ApiResult getNewCoupon(String token,@RequestBody List<Integer> couponids){
        ApiResult<Object> result = null;
        try {
            result = new ApiResult<>();
            userService.addNewCoupon(token,couponids);
            result.setMessage("领取新人优惠劵成功");
        } catch (NoopsycheException e) {
            e.printStackTrace();
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage(e.getMessage());
            result.setCode(e.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "查看所有使用中的非新人优惠劵",notes = "查看非新人优惠劵",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/loadCoupon")
    public ApiResult loadCoupon(){
        ApiResult<Object> result = null;
        try {
            result = new ApiResult<>();
            List<Coupon> couponList=userService.findAllCoupon();
            result.setMessage("查看非新人优惠劵成功");
            result.setData(couponList);
        } catch (NoopsycheException e) {
            e.printStackTrace();
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "兑换优惠劵",notes = "兑换优惠劵",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/getCoupon")
    public ApiResult getCoupon(@RequestBody List<UserCoupon> userCouponList){
            ApiResult<Object> result = null;
        try {
            result = new ApiResult<>();
            userService.addCoupon(userCouponList);
            result.setMessage("兑换优惠劵成功");
        } catch (NoopsycheException e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setCode(e.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "兑换优惠劵回调，付款成功后回调",notes = "付款成功后回调",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/annocyCoupon")
    public ApiResult Coupon(@RequestBody List<UserCoupon> userCouponList){
        ApiResult<Object> result = null;
        try {
            result = new ApiResult<>();
            userService.addAnnocyCoupon(userCouponList);
            result.setMessage("兑换优惠劵成功");
        } catch (NoopsycheException e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setCode(e.getStatusCode());
        }catch (Exception e) {
            e.printStackTrace();
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "查看下级",notes = "查看下级",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/loadNext")
    public ApiResult loadNext(String token,Integer page,Integer limit){
        ApiResult<PageInfo<WxUser>> result = new ApiResult<>();
        try {
            PageInfo<WxUser> pageInfo = userService.findNext(token, page, limit);
            result.setMessage("查看下级成功");
            result.setData(pageInfo);
        } catch (NoopsycheException e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setCode(e.getStatusCode());
        } catch (Exception e){
            e.printStackTrace();
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
}