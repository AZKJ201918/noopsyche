package com.azkj.noopsyche.service.Impl;

import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.dao.CommodityMapper;
import com.azkj.noopsyche.dao.ProductMapper;
import com.azkj.noopsyche.dao.PropertyMapper;
import com.azkj.noopsyche.entity.Commodity;
import com.azkj.noopsyche.entity.Property;
import com.azkj.noopsyche.entity.Sku;
import com.azkj.noopsyche.service.CommodityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.searchbox.client.http.JestHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private PropertyMapper propertyMapper;

    @Autowired
    private ProductMapper productMapper;


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
        //查询规格名称
        List<Property> propertyList=propertyMapper.selectByPropertyAll(id);
        propertyList.stream().forEach(
                parm->{
                    //查询规格详情
                    parm.setProductList(productMapper.selectByProductAll(parm.getId()));
                }
        );
        commodity.setPropertyList(propertyList);

        return commodity;
    }

    @Override
    public Sku searchCommodity(String skuname) throws NoopsycheException {
        return null;
    }
}
