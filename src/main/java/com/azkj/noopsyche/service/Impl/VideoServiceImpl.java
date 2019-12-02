package com.azkj.noopsyche.service.Impl;

import com.azkj.noopsyche.dao.VideoMapper;
import com.azkj.noopsyche.dao.VideodetailsMapper;
import com.azkj.noopsyche.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("videoServiceImpl")
public class VideoServiceImpl implements VideoService{

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private VideodetailsMapper videodetailsMapper;
}
