package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.Sku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ShopCarMapper {
    @Select("select spuid,repertory from sku where id=#{skuid}")
    Sku selectSkuBySkuid(String skuid);
    @Update("update commodity set status=0 where id=#{spuid}")
    int updateCommodityStatus(Integer spuid);
    @Select("select spuid,repertory,skuprice from sku where id=#{skuId}")
    Sku selectSkuWithPriceBySkuid(String skuId);
}
