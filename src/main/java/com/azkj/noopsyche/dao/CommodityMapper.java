package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.Commodity;
import com.azkj.noopsyche.entity.Flag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface CommodityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Commodity record);

    int insertSelective(Commodity record);

    Commodity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Commodity record);

    int updateByPrimaryKey(Commodity record);

    List<Commodity> selectAllCommodity(@Param("flag") Integer flag, @Param("sort") Integer sort);

    List<Commodity> selectCommodity(Integer flag);
    @Select("select id,flagname,status from flag where status=1")
    List<Flag> selectFlag();
}