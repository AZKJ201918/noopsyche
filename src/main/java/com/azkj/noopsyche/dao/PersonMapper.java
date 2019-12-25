package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.Person;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PersonMapper {
    @Insert("insert into person (id,view) values (null,#{view})")
    int insertPerson(Person person);
    @Select("select id,view,status from person")
    Person selectMyView();
}
