package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.Sku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SkuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Sku record);

    int insertSelective(Sku record);

    Sku selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Sku record);

    int updateByPrimaryKey(Sku record);
    @Update("update sku set repertory=repertory-#{num} where id=#{skuId}")
    int updateRepertory(@Param("skuId") Integer skuId,@Param("num") Integer num);
}