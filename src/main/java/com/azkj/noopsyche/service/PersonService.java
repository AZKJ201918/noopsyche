package com.azkj.noopsyche.service;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.entity.Person;

public interface PersonService {
    void addPerson(Person person);

    Person findMyView() throws NoopsycheException;
}
