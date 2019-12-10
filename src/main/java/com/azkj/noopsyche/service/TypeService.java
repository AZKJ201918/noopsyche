package com.azkj.noopsyche.service;

import java.util.Map;

public interface TypeService {
    public Map<String, Object> query(String queryString, String sort, int start, int pageSize) throws Exception;
}
