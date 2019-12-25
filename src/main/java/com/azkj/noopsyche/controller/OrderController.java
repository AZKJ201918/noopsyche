package com.azkj.noopsyche.controller;

import com.alibaba.fastjson.JSON;
import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.jms.SmsProcessor;
import com.azkj.noopsyche.common.resp.ApiResult;
import com.azkj.noopsyche.entity.Orders;
import com.azkj.noopsyche.entity.UserCoupon;
import com.azkj.noopsyche.service.OrderService;
import com.azkj.noopsyche.service.UserCouponService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@CrossOrigin
@Api(value = "订单模块")
public class OrderController {
    @Autowired
    private SmsProcessor smsProcessor;
    @Autowired
    @Qualifier("orderServiceImpl")
    private OrderService orderService;
    @Autowired
    @Qualifier("userCouponServiceImpl")
    private UserCouponService userCouponService;

    @ApiOperation(value = "生成订单",notes = "生成订单",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/createOrderInLine")
    public ApiResult createOrderInLine(@RequestBody Map<String,Object> dataMap){
        ApiResult<Object> result = new ApiResult<>();
        /*Destination destination = new ActiveMQQueue("aaa");
        String s = JSON.toJSONString(dataMap);
        smsProcessor.sendSmsToQueue(destination,s);*/
        try {
            orderService.addOrders(dataMap);
            result.setMessage("订单新增成功");
        } catch (NoopsycheException e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setData(e.getStatusCode());
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "查看所有订单",notes = "查看订单",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/loadOrder")
    public ApiResult<PageInfo<Orders>> loadOrder(Integer page,Integer limit,@RequestBody Orders orders){
        ApiResult<PageInfo<Orders>> result = new ApiResult<>();
        /*Destination destination = new ActiveMQQueue("aaa");
        String s = JSON.toJSONString(dataMap);
        smsProcessor.sendSmsToQueue(destination,s);*/
        try {
            PageInfo<Orders> pageInfo = orderService.findAllOrder(page, limit,orders);
            result.setMessage("查看订单成功");
            result.setData(pageInfo);
        } catch (NoopsycheException e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setCode(e.getStatusCode());
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "取消订单",notes = "取消订单，根据订单号",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/modifyOrder")
    public ApiResult modifyOrder(String orderId){
        ApiResult<Object> result = new ApiResult<>();
        /*Destination destination = new ActiveMQQueue("aaa");
        String s = JSON.toJSONString(dataMap);
        smsProcessor.sendSmsToQueue(destination,s);*/
        try {
            orderService.modifyOrder(orderId);
            result.setMessage("取消订单成功");
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "删除订单",notes = "删除订单",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/deleteOrder")
    public ApiResult<Object> deleteOrder(Integer id){
        ApiResult<Object> result = new ApiResult<>();
        /*Destination destination = new ActiveMQQueue("aaa");
        String s = JSON.toJSONString(dataMap);
        smsProcessor.sendSmsToQueue(destination,s);*/
        try {
            orderService.removeOrder(id);
            result.setMessage("删除订单成功");
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "签收订单",notes = "签收订单",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/signOrder")
    public ApiResult<Object> signOrder(Integer id){
        ApiResult<Object> result = new ApiResult<>();
        /*Destination destination = new ActiveMQQueue("aaa");
        String s = JSON.toJSONString(dataMap);
        smsProcessor.sendSmsToQueue(destination,s);*/
        try {
            orderService.modifyOrderToSign(id);
            result.setMessage("签收订单成功");
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "查看订单详情",notes = "查看订单详情",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/loadOrderDetail")
    public ApiResult<PageInfo<Orders>> loadOrderDetail(String orderId,Integer id){
        ApiResult<PageInfo<Orders>> result = new ApiResult<>();
        /*Destination destination = new ActiveMQQueue("aaa");
        String s = JSON.toJSONString(dataMap);
        smsProcessor.sendSmsToQueue(destination,s);*/
        try {
            Orders order= orderService.findOneOrderDetail(orderId,id);
            result.setMessage("查看订单详情成功");
        } catch (NoopsycheException e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage(e.getMessage());
            result.setCode(e.getStatusCode());
        }catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "查看用户未过期优惠券信息",notes = "查看未过期优惠券信息",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/loadUserCoupon")
    public ApiResult loadUserCoupon(String token){
        ApiResult result = new ApiResult<>();
        /*Destination destination = new ActiveMQQueue("aaa");
        String s = JSON.toJSONString(dataMap);
        smsProcessor.sendSmsToQueue(destination,s);*/
        try {
            List<UserCoupon> userCouponList= userCouponService.findAllCoupon(token);
            result.setMessage("查看未过期优惠券成功");
            result.setData(userCouponList);
        } catch (NoopsycheException e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setCode(e.getStatusCode());
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "订单回调",notes = "订单回调",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/annocy")
    public Map purchaseMini(HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> map=new HashMap<>();
        String inputLine = "";
        String notityXml = "";
        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notityXml += inputLine;
            }
            request.getReader().close();
            orderService.notifyurl(notityXml);
            map.put("return_code","01");
            map.put("return_msg","回调成功");
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);

        }
        return map;
    }
}
