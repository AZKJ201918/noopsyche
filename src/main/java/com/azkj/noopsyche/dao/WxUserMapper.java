package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.Coupon;
import com.azkj.noopsyche.entity.ExchangePea;
import com.azkj.noopsyche.entity.WxUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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
    @Select("select id,type,subtract,discount,fullsubtract,fulls,fulldiscount,fulld,day from coupon where `use`=1 and status=0")
    List<Coupon> selectAllNewCoupon();
    @Select("select day from coupon where id=#{couponid}")
    Integer selectOutDayByCouponid(Integer couponid);
    @Select("select id,type,subtract,discount,fullsubtract,fulls,fulldiscount,fulld,day,money,integral,pea,status from coupon where outtime>now() or outtime is null and `use`=1 and status!=0")
    List<Coupon> selectAllCoupon();
}