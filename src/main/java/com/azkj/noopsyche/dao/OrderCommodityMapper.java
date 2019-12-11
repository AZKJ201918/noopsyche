package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.OrderCommodity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderCommodityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderCommodity record);

    int insertSelective(OrderCommodity record);

    OrderCommodity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderCommodity record);

    int updateByPrimaryKey(OrderCommodity record);
}