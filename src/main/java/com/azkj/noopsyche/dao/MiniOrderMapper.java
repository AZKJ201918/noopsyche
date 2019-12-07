package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.MiniOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MiniOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MiniOrder record);

    int insertSelective(MiniOrder record);

    MiniOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MiniOrder record);

    int updateByPrimaryKey(MiniOrder record);

    @Select("Select id, token, mid, createtime, price, endtime, status, preferential, orderid from miniorder where orderid=#{orderid}")
    MiniOrder SelectOrderId(String orderId);
}