package com.azkj.noopsyche.service.Impl;

import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.utils.SearchUtils;
import com.azkj.noopsyche.dao.AssembleMapper;
import com.azkj.noopsyche.dao.CommodityMapper;
import com.azkj.noopsyche.dao.ProductMapper;
import com.azkj.noopsyche.dao.PropertyMapper;
import com.azkj.noopsyche.entity.Assemble;
import com.azkj.noopsyche.entity.Commodity;
import com.azkj.noopsyche.entity.Property;
import com.azkj.noopsyche.entity.Sku;
import com.azkj.noopsyche.service.CommodityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.searchbox.client.http.JestHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    @Autowired
    private AssembleMapper assembleMapper;

    @Autowired
    private SearchUtils searchUtils;

    @Override
    public PageInfo<Commodity> findAllCommodity(Integer page, Integer limit, Integer flag) throws NoopsycheException {
        PageHelper.startPage(page,limit);
        List<Commodity> commodityList = commodityMapper.selectAllCommodity(flag);
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
        Assemble assemble=assembleMapper.selectAssembleBySpuid(id);
        commodity.setAssemble(assemble);
        return commodity;
    }

    @Override
    public Sku searchCommodity(String search) throws NoopsycheException, IOException {
        return searchUtils.SearchSku(search);
    }
}
