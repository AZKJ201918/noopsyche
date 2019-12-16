package com.azkj.noopsyche.controller;

import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.resp.ApiResult;
import com.azkj.noopsyche.entity.Commodity;
import com.azkj.noopsyche.entity.Sku;
import com.azkj.noopsyche.service.CommodityService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@Api(value = "商品模块")
public class CommodityController {
    @Autowired
    @Qualifier("commodityServiceImpl")
    private CommodityService commodityService;
    @ApiOperation(value = "查看商品", notes = "查看商品", httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/commodity")
    public ApiResult commodity(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "8") Integer limit, Integer flag){
        ApiResult<Object> result = new ApiResult<>();
        try {
            PageInfo<Commodity> pageInfo = commodityService.findAllCommodity(page, limit, flag);
            result.setMessage("查看商品成功");
            result.setData(pageInfo);
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
    @ApiOperation(value = "查看商品详情", notes = "查看商品详情", httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/commodityDetail")
    public ApiResult commodityDetail(Integer id){
        ApiResult<Object> result = new ApiResult<>();
        try {
            Commodity commodity= commodityService.findAllCommodityDetail(id);
            result.setMessage("查看商品详情成功");
            result.setData(commodity);
        } catch (NoopsycheException e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setCode(e.getStatusCode());
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }

    @ApiOperation(value = "检索商品", notes = "检索商品", httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/searchCommodity")
    public ApiResult searchCommodity(String skuname){
        ApiResult<Sku> result = new ApiResult<>();
        try {
            Sku sku= commodityService.searchCommodity(skuname);
            result.setMessage("检索商品成功");
            result.setData(sku);
        } catch (NoopsycheException e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setCode(e.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
}
