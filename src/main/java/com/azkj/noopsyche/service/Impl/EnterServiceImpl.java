package com.azkj.noopsyche.service.Impl;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.dao.EnterMapper;
import com.azkj.noopsyche.entity.Enter;
import com.azkj.noopsyche.service.EnterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("enterServiceImpl")
public class EnterServiceImpl implements EnterService {

    @Autowired
    private EnterMapper enterMapper;

    @Override
    public void merchantEnter(Enter enter) {
        enterMapper.insertSelective(enter);
    }

    @Override
    public Enter Viewaudit(Integer id) throws NoopsycheException {
        return enterMapper.selectByPrimaryKey(id);
    }
}
