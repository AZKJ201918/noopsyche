package com.azkj.noopsyche.service;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.entity.Orders;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface OrderService {


    Map<String, String> addOrders(Map<String, Object> dataMap) throws Exception;

    PageInfo<Orders> findAllOrder(Integer page, Integer limit, String token,Integer status) throws NoopsycheException;

    void modifyOrder(String orderId);

    void removeOrder(Integer id);

    void modifyOrderToSign(Integer id);

    void notifyurl(String notityXml) throws Exception;

    Orders findOneOrderDetail(String orderId, Integer id) throws NoopsycheException;
}
