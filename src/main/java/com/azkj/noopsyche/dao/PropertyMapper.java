package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.Property;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PropertyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Property record);

    int insertSelective(Property record);

    Property selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Property record);

    int updateByPrimaryKey(Property record);

    @Select("SELECT id,propertyname FROM property where spuid=#{id}")
    List<Property> selectByPropertyAll(Integer id);
}