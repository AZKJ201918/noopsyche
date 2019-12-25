package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);

    @Select("select phone,name from address where status=0 and token=#{token}")
    Address selectStatusByToken(String token);

    @Update("update address set status=1 where status=0 and token=#{token}")
    int updateToFeiMoren(String token);

    List<Address> selectByToken(String token);
}