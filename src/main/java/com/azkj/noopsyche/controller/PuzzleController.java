package com.azkj.noopsyche.controller;

import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.resp.ApiResult;
import com.azkj.noopsyche.entity.Assemble;
import com.azkj.noopsyche.entity.Groups;
import com.azkj.noopsyche.entity.People;
import com.azkj.noopsyche.service.PuzzleService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin
@Api(value = "拼团模块")
public class PuzzleController {
    @Autowired
    private PuzzleService puzzleService;

    @ApiOperation(value = "创建拼团",notes = "创建拼团",httpMethod = "POST")
    @ApiImplicitParam//已测试
    @PostMapping("/createPuzzle")
    public ApiResult createPuzzle(String token,Integer assembleId){
        ApiResult<Object> result = new ApiResult<>();
        try {
            puzzleService.addPuzzle(token,assembleId);
            result.setMessage("创建拼团成功");
        } catch (NoopsycheException e) {
            result.setMessage(e.getMessage());
            result.setCode(e.getStatusCode());
        } catch (ParseException e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("后台错误");
        }
        return result;
    }
    @ApiOperation(value = "查看某个商品的拼团信息",notes = "查看某个商品的拼团信息",httpMethod = "POST")
    @ApiImplicitParam//已测试
    @PostMapping("/loadAssemble")
    public ApiResult<List<Assemble>> loadAssemble(Integer spuid){
        ApiResult<List<Assemble>> result = new ApiResult<>();
        try {
            List<Assemble> assembleList=puzzleService.findAssembleBySpuid(spuid);
            result.setMessage("查看某个商品的拼团信息成功");
            result.setData(assembleList);
        } catch (NoopsycheException e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setCode(e.getStatusCode());
        }catch (Exception e) {
            log.error("SQL statement error or that place is empty"+e);
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "加入拼团",notes = "加入拼团",httpMethod = "POST")
    @ApiImplicitParam/*(name = "gid，uid",required = true,dataType = "INT")*/
    @PostMapping("joinGroup")
    public  ApiResult joinGroup(Integer gid,String token){
        ApiResult result=new ApiResult();
        try {//已测试
            puzzleService.JoinGroup(gid,token);
            result.setMessage("加入拼团成功");
        }catch (NoopsycheException e){
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        }catch (Exception e){
            log.error("SQL statement error or that place is empty"+e);
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }
    @ApiOperation(value = "查看我的拼团",notes = "查看我的拼团",httpMethod = "POST")
    @ApiImplicitParam/*(name = "gid，uid",required = true,dataType = "INT")*/
    @PostMapping("loadGroup")
    public  ApiResult<PageInfo<People>> loadGroup(String token,Integer page,Integer limit,Integer status){
        ApiResult<PageInfo<People>> result=new ApiResult();
        try {
            PageInfo<People> pageInfo = puzzleService.findGroup(token, page, limit,status);
            result.setMessage("加入成功");
            result.setData(pageInfo);
        }catch (NoopsycheException e){
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        }catch (Exception e){
            log.error("SQL statement error or that place is empty"+e);
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }
    @ApiOperation(value = "查看某个商品的拼团",notes = "查看某个商品的拼团",httpMethod = "POST")
    @ApiImplicitParam/*(name = "spuid",required = true,dataType = "INT")*/
    @PostMapping("selectGroup")
    public ApiResult<PageInfo<Groups>> selGroup(Integer spuid,Integer page,Integer limit){
        ApiResult<PageInfo<Groups>> result = new ApiResult<>();
        try {
            PageInfo<Groups> group = puzzleService.findSpuGroup(spuid, page, limit);
            result.setMessage("查看拼团成功");
            result.setData(group);
        } catch (NoopsycheException e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setCode(e.getStatusCode());
        }catch (Exception e){
            log.error("SQL statement error or that place is empty"+e);
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }
    @ApiOperation(value = "查看某个拼团",notes = "查看某个拼团",httpMethod = "POST")
    @ApiImplicitParam/*(name = "spuid",required = true,dataType = "INT")*/
    @PostMapping("selectGroupByGid")
    public ApiResult GroupByGid(Integer gid){
        ApiResult<Object> result = new ApiResult<>();
        Groups groups=puzzleService.findPuzzleByGid(gid);
        return result;
    }
}
