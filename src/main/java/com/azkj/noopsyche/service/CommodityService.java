package com.azkj.noopsyche.service;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.entity.Commodity;
import com.azkj.noopsyche.entity.Flag;
import com.azkj.noopsyche.entity.Sku;
import com.github.pagehelper.PageInfo;

import java.io.IOException;
import java.util.List;

public interface CommodityService {
    PageInfo<Commodity> findAllCommodity(Integer page, Integer limit, Integer flag, Integer sort) throws NoopsycheException;

    Commodity findAllCommodityDetail(Integer id) throws NoopsycheException;

    Sku searchCommodity(String search) throws NoopsycheException, IOException;

    List<Commodity> findCommodity(Integer flag) throws NoopsycheException;

    List<Flag> findFlag() throws NoopsycheException;
}
