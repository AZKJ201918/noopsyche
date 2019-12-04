package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.Bank;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BankMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bank record);

    int insertSelective(Bank record);

    Bank selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bank record);

    int updateByPrimaryKey(Bank record);
    @Select("select bankid from bank where status=0 and token=#{token}")
    Integer selectByToken(String token);
    @Select("select id,bankid,token,status from bank where token =#{token}")
    List<Bank> selectBank(String token);
    @Delete("delete from bank where bankid=#{bankid}")
    int deleteByBankId(Bank bank);
    @Update("update bank set status=1 where status=0 and token=#{token}")
    int updateStatusToFeiMonren(String token);
    @Update("update bank set status=0 where bankid=#{bankid}")
    int updateByBankId(Bank bank);
}