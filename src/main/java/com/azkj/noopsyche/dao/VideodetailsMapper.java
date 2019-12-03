package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.Videodetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VideodetailsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Videodetails record);

    int insertSelective(Videodetails record);

    Videodetails selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Videodetails record);

    int updateByPrimaryKey(Videodetails record);

    @Select("SELECT name, url FROM videodetails  WHERE vid=#{vid} AND status=1")
    List<Videodetails> SelectVideoDetails(Integer vid);
}