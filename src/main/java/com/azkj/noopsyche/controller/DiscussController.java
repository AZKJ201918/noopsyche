package com.azkj.noopsyche.controller;

import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.resp.ApiResult;
import com.azkj.noopsyche.entity.Discuss;
import com.azkj.noopsyche.service.DiscussService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@CrossOrigin
@Api(value = "评论模块")
public class DiscussController {
    @Autowired
    private DiscussService discussService;
    @ApiOperation(value = "新增评论",notes = "新增评论",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/addDiscuss")
    public ApiResult addDiscuss(@RequestBody List<Discuss> discussList, HttpServletRequest request){
        String token = request.getParameter("token");
        ApiResult<Object> result = new ApiResult<>();
        try {
            discussService.addDiscuss(discussList,token);
            result.setMessage("评论新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "查看商品评论",notes = "商品评论",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/loadDiscuss")
    public ApiResult loadDiscuss(Integer id,Integer page,Integer evaluate){
        ApiResult<Map<String,Object>> result = new ApiResult<>();
        try {
            HashMap<String, Object> discussMap = discussService.findDiscuss(id, page, evaluate);
            result.setMessage("查看商品评论成功");
            result.setData(discussMap);
        }catch (NoopsycheException e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setCode(e.getStatusCode());
        }catch (Exception e) {
            e.printStackTrace();
            log.error("SQL statement error or that place is empty" + e);
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }
    @ApiOperation(value = "追加评论",notes = "追加评论",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/plusDiscuss")
    public ApiResult plusDiscuss(@RequestBody List<Discuss> discussList){
        ApiResult<Object> result = new ApiResult<>();
        try {
            discussService.plusDiscuss(discussList);
            result.setMessage("追加评论成功");
        } catch (NoopsycheException e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setCode(e.getStatusCode());
        }catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
}
