package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.Datum;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DatumMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Datum record);

    int insertSelective(Datum record);

    Datum selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Datum record);

    int updateByPrimaryKey(Datum record);
}