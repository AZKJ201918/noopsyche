package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.Assemble;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AssembleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Assemble record);

    int insertSelective(Assemble record);

    Assemble selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Assemble record);

    int updateByPrimaryKey(Assemble record);
    @Select("select id,size,discount from assemble where status=1 and spuid=#{spuid}")
    List<Assemble> selectAssembleBySpuid(Integer spuid);
}