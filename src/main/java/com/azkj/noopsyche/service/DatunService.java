package com.azkj.noopsyche.service;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.entity.Datum;

public interface DatunService {
    void InstrDatun(Datum datum)throws NoopsycheException;

    Datum SelectDatun(String token) throws NoopsycheException;
}
