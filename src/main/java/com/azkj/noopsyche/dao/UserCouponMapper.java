package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.UserCoupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;
@Mapper
public interface UserCouponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserCoupon record);

    int insertSelective(UserCoupon record);

    UserCoupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserCoupon record);

    int updateByPrimaryKey(UserCoupon record);

    List<UserCoupon> selectByToken(String token);
    @Update("update usercoupon set status=1 where id=#{userCouponId}")
    int updateStatus(Integer userCouponId);
}