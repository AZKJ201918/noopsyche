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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@CrossOrigin
@Api(value = "资料上传模块")
public class DatumController {


    @Autowired
    @Qualifier("datunServiceImpl")
    private DatunService datunService;


    @ApiOperation(value = "资料上传",notes = "资料上传",httpMethod = "POST")
    @ApiImplicitParam
    @DeleteMapping("/InstrDatun")
    public ApiResult InstrDatun(Datum datum){
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
    @DeleteMapping("/SelectDatun")
    public ApiResult SelectDatun(String token){
        ApiResult<Datum> result=new ApiResult();
        try {
            Datum datum=datunService.SelectDatun(token);
            result.setData(datum);
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
}
