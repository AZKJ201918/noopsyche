package com.azkj.noopsyche.service.Impl;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.dao.VideoEmployMapper;
import com.azkj.noopsyche.dao.VideoMapper;
import com.azkj.noopsyche.dao.VideodetailsMapper;
import com.azkj.noopsyche.entity.Video;
import com.azkj.noopsyche.entity.VideoEmploy;
import com.azkj.noopsyche.entity.Videodetails;
import com.azkj.noopsyche.entity.WxUser;
import com.azkj.noopsyche.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

@Service("videoServiceImpl")
public class VideoServiceImpl implements VideoService{

    private final static String CHECK_STATE_TUTION="1";//上级

    private final static String CHECK_STATE_SUSPEND="2";//下架

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private VideodetailsMapper videodetailsMapper;

    @Autowired
    private VideoEmployMapper videoEmployMapper;

    @Override
    public List<Video> SelectVideo() throws NoopsycheException {
        List<Video> voidList=videoMapper.SelectVideo();
        if(!CollectionUtils.isEmpty(voidList)){
            throw  new NoopsycheException(400,"没有视频");
        }
        return voidList;
    }

    @Override
    public List<Videodetails> SelectVideoDetails(String token,Integer vid) throws NoopsycheException {
        VideoEmploy videoEmploy=videoEmployMapper.SelectVideoEmploy(token,vid);
        if(videoEmploy==null){
            throw  new NoopsycheException(300,"你没有看这个视频的权限");
        }
        List<Videodetails> videodetailsList=videodetailsMapper.SelectVideoDetails(vid);
        if(CollectionUtils.isEmpty(videodetailsList)){
            throw  new NoopsycheException(400,"没有视频");
        }
        return videodetailsList;
    }

    @Override
    public void ConversionVideo(VideoEmploy videoEmploy) throws NoopsycheException {
        VideoEmploy video=videoEmployMapper.SelectVideoEmploy(videoEmploy.getToken(),videoEmploy.getVid());
        if(video!=null){
            throw new NoopsycheException(400,"你已经兑换过这个课程了");
        }
        Video videos=videoMapper.selectByPrimaryKey(videoEmploy.getVid());
        if (CHECK_STATE_SUSPEND.equals(videos.getStatus())) {
           throw new NoopsycheException(400,"课程已经下架");
        }
        videoEmployMapper.insertSelective(videoEmploy);

    }
}
