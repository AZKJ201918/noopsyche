package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.Sku;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SkuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Sku record);

    int insertSelective(Sku record);

    Sku selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Sku record);

    int updateByPrimaryKey(Sku record);
}