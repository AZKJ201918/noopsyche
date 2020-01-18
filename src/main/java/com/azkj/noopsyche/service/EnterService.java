package com.azkj.noopsyche.service;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.entity.Enter;

public interface EnterService {
    void merchantEnter(Enter enter)throws NoopsycheException;

    Enter Viewaudit(Integer id)throws NoopsycheException;
}
