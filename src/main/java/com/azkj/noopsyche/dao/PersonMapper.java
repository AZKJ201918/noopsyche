package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.Person;
import org.apache.ibatis.annotations.Insert;

public interface PersonMapper {
    @Insert("insert into person (id,view) values (null,#{view})")
    int insertPerson(Person person);

    Person selectMyView();
}
