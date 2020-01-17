package com.azkj.noopsyche.service.Impl;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("personServiceImpl")
public class PersonServiceImpl implements PersonService {

//    @Autowired
//    private PersonMapper personMapper;
//
//    @Override
//    public void addPerson(Person person) {
//        personMapper.insertPerson(person);
//    }
//
//    @Override
//    public Person findMyView() throws NoopsycheException {
//        Person person = personMapper.selectMyView();
//        if (person==null){
//            throw new NoopsycheException("没有页面信息");
//        }
//        return person;
//    }
}
