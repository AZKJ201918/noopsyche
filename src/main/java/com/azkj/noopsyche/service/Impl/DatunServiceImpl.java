package com.azkj.noopsyche.service.Impl;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.dao.DatumMapper;
import com.azkj.noopsyche.entity.Datum;
import com.azkj.noopsyche.service.DatunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("datunServiceImpl")
public class DatunServiceImpl implements DatunService{

   private final static Integer CHECK_STATE_REVIVE=1;

    private final static Integer CHECK_STATE_NOPASS=2;

    private final static Integer CHECK_STATE_TRANSIT =3;



    @Autowired
    private DatumMapper datumMapper;

    @Override
    public void InstrDatun(Datum datum) throws NoopsycheException {
        datum.setCreatetime(new Date());
        datum.setStatus(CHECK_STATE_REVIVE);
        datumMapper.insertSelective(datum);
    }

    @Override
    public Datum SelectDatun(String token) {

        return null;
    }
}
