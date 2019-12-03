package com.azkj.noopsyche.service;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.entity.Video;
import com.azkj.noopsyche.entity.Videodetails;
import java.util.List;

public interface VideoService {
    List<Video> SelectVideo() throws NoopsycheException;

    List<Videodetails> SelectVideoDetails(String token,Integer vid) throws NoopsycheException;
}
