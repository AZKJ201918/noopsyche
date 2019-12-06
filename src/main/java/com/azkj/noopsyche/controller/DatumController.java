package com.azkj.noopsyche.controller;


import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.resp.ApiResult;
import com.azkj.noopsyche.entity.Datum;
import com.azkj.noopsyche.service.DatunService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin
@Api(value = "资料上传模块")
public class DatumController {


    @Autowired
    @Qualifier("datunServiceImpl")
    private DatunService datunService;


    @ApiOperation(value = "资料上传",notes = "资料上传",httpMethod = "PUT")
    @ApiImplicitParam
    @PutMapping("/instrDatun")
    public ApiResult InstrDatun(@RequestBody Datum datum){
        ApiResult result=new ApiResult();
        try {
            datunService.InstrDatun(datum);
            result.setMessage("资料上传中审核中");
        } catch (NoopsycheException e) {
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }

    @ApiOperation(value = "查看上传资料",notes = "查看上传资料",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/selectDatun")
    public ApiResult SelectDatun(String token){
        ApiResult<List<Datum>> result=new ApiResult();
        try {
            List<Datum> datumList=datunService.SelectDatun(token);
            result.setData(datumList);
            result.setMessage("查看资料成功");
        } catch (NoopsycheException e) {
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }




    @ApiOperation(value = "查看上传资料图片",notes = "查看上传图片",httpMethod = "PUT")
    @ApiImplicitParam
    @PutMapping("/uploading")
    public ApiResult Uploading(HttpServletRequest req, @RequestParam(required=false ) MultipartFile file){
        ApiResult result=new ApiResult();
        try {
            String url=datunService.Uploading(file);
            result.setData(url);
            result.setMessage("上传资料成功");
        } catch (NoopsycheException e) {
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }

}
