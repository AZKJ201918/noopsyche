package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.Mini;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MiniMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Mini record);

    int insertSelective(Mini record);

    Mini selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Mini record);

    int updateByPrimaryKey(Mini record);

    @Select("SELECT id,mininame,price,url  FROM mini  WHERE status=1")
    List<Mini> SelectMiniAll();
}