package com.azkj.noopsyche.service;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.entity.Datum;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DatunService {
    void InstrDatun(Datum datum)throws NoopsycheException;

    List<Datum> SelectDatun(String token) throws NoopsycheException;

    String Uploading(MultipartFile file)throws Exception;
}
