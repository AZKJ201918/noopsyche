package com.azkj.noopsyche.service;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.entity.Datum;

import java.util.List;

public interface DatunService {
    void InstrDatun(Datum datum)throws NoopsycheException;

    List<Datum> SelectDatun(String token) throws NoopsycheException;
}
