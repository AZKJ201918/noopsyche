package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VideoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);

    @Select("Select id, name, username, createtime, endtime, status, introduce, mark FROM video WHERE CURDATE()<endtime AND status=1")
    List<Video> SelectVideo();
}