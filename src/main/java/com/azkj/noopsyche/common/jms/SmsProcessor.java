package com.azkj.noopsyche.common.jms;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.utils.RedisUtil;
import com.azkj.noopsyche.controller.OrderController;
import com.azkj.noopsyche.dao.ShopCarMapper;
import com.azkj.noopsyche.dao.SkuMapper;
import com.azkj.noopsyche.entity.Sku;
import com.azkj.noopsyche.service.Impl.OrderServiceImpl;
import com.azkj.noopsyche.service.OrderService;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Component
public class SmsProcessor {

    @Autowired
    private  JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private ShopCarMapper shopCarMapper;

    @Autowired
    @Qualifier("orderServiceImpl")
    private OrderService orderService;
    public void sendSmsToQueue(Destination destination, String message) {
        jmsMessagingTemplate.convertAndSend(destination,message);
    }

    @JmsListener(destination = "aaa")
    public  void  aaa(String text)  {
        List<Map> lists = JSONArray.parseArray(text, Map.class);
        for (Map map:lists) {
            Integer skuId = (Integer) map.get("skuId");
            Integer num = (Integer) map.get("num");
            Integer repertory = (Integer) redisUtil.getObject("repertory:" + skuId);
            if (repertory == null) {
                Sku sku = shopCarMapper.selectSkuBySkuid(String.valueOf(skuId));
                repertory = sku.getRepertory();
            }
            repertory -= num;
            skuMapper.updateRepertory(skuId, num);
            redisUtil.setObjectLong("repertory:" + skuId, repertory);
        }
    }
}
