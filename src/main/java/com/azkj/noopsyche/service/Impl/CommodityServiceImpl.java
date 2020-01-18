package com.azkj.noopsyche.service.Impl;

import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.utils.SearchUtils;
import com.azkj.noopsyche.dao.AssembleMapper;
import com.azkj.noopsyche.dao.CommodityMapper;
import com.azkj.noopsyche.dao.ProductMapper;
import com.azkj.noopsyche.dao.PropertyMapper;
import com.azkj.noopsyche.entity.*;
import com.azkj.noopsyche.service.CommodityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private PropertyMapper propertyMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private AssembleMapper assembleMapper;

    @Autowired
    private SearchUtils searchUtils;

    @Override
    public PageInfo<Commodity> findAllCommodity(Integer page, Integer limit, Integer flag, Integer sort) throws NoopsycheException {
        PageHelper.startPage(page,limit);
        List<Commodity> commodityList = commodityMapper.selectAllCommodity(flag,sort);
        if (commodityList==null||commodityList.size()==0){
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
        //查询规格名称
        List<Property> propertyList=propertyMapper.selectByPropertyAll(id);
        propertyList.stream().forEach(
                parm->{
                    //查询规格详情
                    parm.setProductList(productMapper.selectByProductAll(parm.getId()));
                }
        );
        commodity.setPropertyList(propertyList);
        List<Assemble> assembleList = assembleMapper.selectAssembleBySpuid(id);
        commodity.setAssembleList(assembleList);
        return commodity;
    }

    @Override
    public Sku searchCommodity(String search) throws NoopsycheException, IOException {
        return searchUtils.SearchSku(search);
    }

    @Override
    public List<Commodity> findCommodity() throws NoopsycheException {
        List<Commodity> commodityList = commodityMapper.selectCommodity();
        if (commodityList==null||commodityList.size()==0){
            throw new NoopsycheException("没有商品信息");
        }
        return commodityMapper.selectCommodity();
    }

    @Override
    public List<Flag> findFlag() throws NoopsycheException {
        List<Flag> flagList = commodityMapper.selectFlag();
        if (flagList==null||flagList.size()==0){
            throw new NoopsycheException("没有分类信息");
        }
        for (Flag flag:flagList){
            List<Commodity> commodityList = commodityMapper.selectAllCommodity(flag.getId(), null);
            flag.setCommodityList(commodityList);
        }
        return flagList;
    }
}
