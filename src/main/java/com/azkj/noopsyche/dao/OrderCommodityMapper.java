package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.OrderCommodity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderCommodityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderCommodity record);

    int insertSelective(OrderCommodity record);

    OrderCommodity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderCommodity record);

    int updateByPrimaryKey(OrderCommodity record);

    List<OrderCommodity> selectByOrderId(String orderId);
}