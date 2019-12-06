package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.ExchangePea;
import com.azkj.noopsyche.entity.WxUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface WxUserMapper {
    int deleteByPrimaryKey(String token);

    int insert(WxUser record);

    int insertSelective(WxUser record);

    WxUser selectByPrimaryKey(String token);

    int updateByPrimaryKeySelective(WxUser record);

    int updateByPrimaryKey(WxUser record);
    @Select("select token,createtime from wxuser where openid=#{openid}")
    WxUser selectUserByOpenId(String openid);
    @Select("select uuid from wxuser where openid=#{openid}")
    String selectSuperioridByOpenid(String openid);
    @Select("select normalexchange,activeexchange,status from exchangepea where use=1")
    ExchangePea selectPea();
    @Update("update wxuser set pea=pea+#{pea} where uuid=#{uuid}")
    int updatePea(@Param("pea") Integer pea,@Param("uuid") String uuid);
}