package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.Groups;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GroupsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Groups record);

    int insertSelective(Groups record);

    Groups selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Groups record);

    int updateByPrimaryKey(Groups record);
    @Select("SELECT a.size FROM groups g INNER JOIN assemble a ON g.aid=a.id WHERE g.id=#{gid}")
    int selectSizeByGid(Integer gid);
    @Update("update groups set gtype=1 where id=#{gid}")
    int updateGtypeByGid(Integer gid);
    @Select("select id,starttime,endtime from groups where gtype=0 and endtime>=now() and spuid=#{spuid}")
    List<Groups> selectBySpuid(Integer spuid);
}