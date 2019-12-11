package com.azkj.noopsyche.service;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.entity.Orders;
import com.github.pagehelper.PageInfo;

import java.text.ParseException;
import java.util.Map;

public interface OrderService {
    void addOrders(Map<String, Object> dataMap) throws NoopsycheException, ParseException;

    PageInfo<Orders> findAllOrder(Integer page, Integer limit, Orders orders) throws NoopsycheException;

    void modifyOrder(String orderId);

    void removeOrder(Integer id);

    void modifyOrderToSign(Integer id);
}
