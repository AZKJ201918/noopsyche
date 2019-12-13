package com.azkj.noopsyche.service;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.entity.ShopCar;
import com.azkj.noopsyche.entity.Sku;

import java.util.List;

public interface ShopCarService {
    void addShopCar(String token,String skuid,Integer num) throws NoopsycheException;

    void modifyShopCar(String token, String skuid, Integer num) throws NoopsycheException;

    List<Sku> findShopCar(String token) throws NoopsycheException;

    void removeShopCar(String token, String id);
}
