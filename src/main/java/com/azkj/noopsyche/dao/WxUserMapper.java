package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.WxUser;
import org.apache.ibatis.annotations.Select;

public interface WxUserMapper {
    int deleteByPrimaryKey(String token);

    int insert(WxUser record);

    int insertSelective(WxUser record);

    WxUser selectByPrimaryKey(String token);

    int updateByPrimaryKeySelective(WxUser record);

    int updateByPrimaryKey(WxUser record);
    @Select("select token from wxuser where openid=#{openid}")
    WxUser selectUserByOpenId(String openid);
    @Select("select uuid from wxuser where openid=#{openid}")
    String selectSuperioridByOpenid(String openid);
}