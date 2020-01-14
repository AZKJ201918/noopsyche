package com.azkj.noopsyche.service;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.entity.Discuss;

import java.util.HashMap;
import java.util.List;

public interface DiscussService {
    void addDiscuss(List<Discuss> discussList, String token);

    HashMap<String, Object> findDiscuss(Integer id, Integer page, Integer evaluate) throws NoopsycheException;

    void plusDiscuss(List<Discuss> discussList) throws NoopsycheException;
}
