package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    @Select("SELECT spuname FROM product WHERE propertyid=#{id} AND status=1")
    List<Product> selectByProductAll(Integer id);
}