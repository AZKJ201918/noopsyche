package com.azkj.noopsyche.controller;

import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.resp.ApiResult;
import com.azkj.noopsyche.entity.Video;
import com.azkj.noopsyche.entity.Videodetails;
import com.azkj.noopsyche.service.VideoService;
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
@CrossOrigin
@Slf4j
@Api(value = "视频模块")
public class VidoController {


    @Autowired
    @Qualifier("videoServiceImpl")
    private VideoService videoService;

    @ApiOperation(value = "培训视频",notes = "培训视频",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/video")
    public ApiResult SelectVideo(){
        ApiResult<List<Video>> result=new ApiResult();
        try {
            List<Video> videoList=videoService.SelectVideo();
            result.setData(videoList);
            result.setMessage("视频查询成功");
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


    @ApiOperation(value = "视频教程",notes = "视频教程",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/videodetails")
    public ApiResult SelectVideoDetails(String token,Integer vid){
        ApiResult<List<Videodetails>> result=new ApiResult();
        try {
            List<Videodetails> videoList=videoService.SelectVideoDetails(token,vid);
            result.setData(videoList);
            result.setMessage("视频查询成功");
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
