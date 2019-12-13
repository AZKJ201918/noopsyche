package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.Coupon;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CouponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);
}