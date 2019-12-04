package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.Register;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface RegisterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Register record);

    int insertSelective(Register record);

    Register selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Register record);

    int updateByPrimaryKey(Register record);
    @Select("select name from register where token=#{token}")
    Register selectRegisterByToken(String token);
    @Update("update register set phone=#{phone} where token=#{token}")
    int updateByToken(Register register);
}