package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.VideoEmploy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface VideoEmployMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VideoEmploy record);

    int insertSelective(VideoEmploy record);

    VideoEmploy selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VideoEmploy record);

    int updateByPrimaryKey(VideoEmploy record);

    @Select("SELECT token,vid FROM videoemploy where  token=#{token} AND vid =#{vid}")
    VideoEmploy SelectVideoEmploy(@Param("token") String token, @Param("vid") Integer vid);
}