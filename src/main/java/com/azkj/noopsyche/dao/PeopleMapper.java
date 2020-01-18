package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.People;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface PeopleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(People record);

    int insertSelective(People record);

    People selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(People record);

    int updateByPrimaryKey(People record);
    @Select("select count(*) from people where gid=#{gid}")
    int selectCount(Integer gid);
    @Select("select id,gid,token from people where gid=#{gid} and token =#{token}")
    People selectPeople(@Param("gid") Integer gid,@Param("token") String token);
    @Select("select id,gid,token,ptype from people where token=#{token}")
    List<People> selectByToken(String token);
    @Select("select id,token,ptype from people where gid=#{gid}")
    List<People> selectPeopleByGid(Integer gid);
}