package com.shopping.mapper;

import com.shopping.entity.WxUser;

public interface WxUserMapper {
    int deleteByPrimaryKey(String token);

    int insert(WxUser record);

    int insertSelective(WxUser record);

    WxUser selectByPrimaryKey(String token);

    int updateByPrimaryKeySelective(WxUser record);

    int updateByPrimaryKey(WxUser record);
}