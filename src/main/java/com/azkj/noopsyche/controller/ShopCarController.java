package com.azkj.noopsyche.controller;

import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.resp.ApiResult;
import com.azkj.noopsyche.entity.ShopCar;
import com.azkj.noopsyche.entity.Sku;
import com.azkj.noopsyche.service.ShopCarService;
import io.swagger.annotations.Api;
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
@Slf4j
@CrossOrigin
@Api(value = "生成购物车")
public class ShopCarController {
    @Autowired
    @Qualifier("shopCarServiceImpl")
    private ShopCarService shopCarService;
    @ApiOperation(value = "生成购物车",notes = "生成购物车",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/insertShopCar")
    public ApiResult insertShopCar(String token,String skuid,Integer num){
        ApiResult<Object> result = new ApiResult<>();
        try {
          shopCarService.addShopCar(token,skuid,num);
          result.setMessage("购物车新增成功");
        }catch (NoopsycheException e){
          e.printStackTrace();
          result.setMessage(e.getMessage());
          result.setCode(e.getStatusCode());
        }catch (Exception e){
          e.printStackTrace();
          result.setMessage("后台服务器异常");
          result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "修改购物车数量",notes = "修改购物车数量",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/updateShopCar")
    public ApiResult updateShopCar(String token,String skuid,Integer num){
        ApiResult<Object> result = new ApiResult<>();
        try {
            shopCarService.modifyShopCar(token,skuid,num);
            result.setMessage("购物车修改成功");
        }catch (NoopsycheException e){
            e.printStackTrace();;
            result.setMessage(e.getMessage());
            result.setCode(e.getStatusCode());
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "查看购物车",notes = "查看购物车",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/loadShopCar")
    public ApiResult loadShopCar(String token){
        ApiResult<List<Sku>> result = new ApiResult<>();
        try {
            List<Sku> skuList=shopCarService.findShopCar(token);
            result.setMessage("查看购物车成功");
            result.setData(skuList);
        }catch (NoopsycheException e){
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setCode(e.getStatusCode());
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "删除购物车",notes = "删除购物车",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/deleteShopCar")
    public ApiResult deleteShopCar(String token,String id){
        ApiResult<Object> result = new ApiResult<>();
        try {
            shopCarService.removeShopCar(token,id);
            result.setMessage("删除购物车成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
}
