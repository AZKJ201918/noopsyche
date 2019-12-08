package com.azkj.noopsyche.service.Impl;

import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.dao.CommodityMapper;
import com.azkj.noopsyche.entity.Commodity;
import com.azkj.noopsyche.service.CommodityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    private CommodityMapper commodityMapper;
    @Override
    public PageInfo<Commodity> findAllCommodity(Integer page, Integer limit, Integer flag) throws NoopsycheException {
        PageHelper.startPage(page,limit);
        List<Commodity> commodityList = commodityMapper.selectAllCommodity(flag);
        if (commodityList==null){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"没有商品信息");
        }
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityList);
        return pageInfo;
    }

    @Override
    public Commodity findAllCommodityDetail(Integer id) throws NoopsycheException {
        Commodity commodity = commodityMapper.selectByPrimaryKey(id);
        if (commodity==null){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"没有商品详情信息");
        }
        return commodity;
    }
}
