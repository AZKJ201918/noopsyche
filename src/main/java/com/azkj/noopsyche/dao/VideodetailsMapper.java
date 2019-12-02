package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.Videodetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VideodetailsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Videodetails record);

    int insertSelective(Videodetails record);

    Videodetails selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Videodetails record);

    int updateByPrimaryKey(Videodetails record);
}