package com.azkj.noopsyche.service;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.entity.Mini;
import com.azkj.noopsyche.entity.MiniOrder;

import java.util.List;
import java.util.Map;


public interface MiniService {
    List<Mini> SelectMini()throws NoopsycheException;

    Mini Minidetails(Integer id)throws NoopsycheException;

    Map<String,String> purchaseMini(MiniOrder miniOrder) throws Exception;

    void mininotifyurl(String notityXml) throws Exception;

    Map<String,String> testpay(String token, Long price) throws Exception;
}
