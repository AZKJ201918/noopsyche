package com.azkj.noopsyche.service;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.entity.Commodity;
import com.azkj.noopsyche.entity.Sku;
import com.github.pagehelper.PageInfo;

import java.io.IOException;

public interface CommodityService {
    PageInfo<Commodity> findAllCommodity(Integer page, Integer limit, Integer flag) throws NoopsycheException;

    Commodity findAllCommodityDetail(Integer id) throws NoopsycheException;

    Sku searchCommodity(String skuname) throws NoopsycheException, IOException;
}
