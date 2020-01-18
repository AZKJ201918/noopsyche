package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.Enter;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EnterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Enter record);

    int insertSelective(Enter record);

    Enter selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Enter record);

    int updateByPrimaryKey(Enter record);
}