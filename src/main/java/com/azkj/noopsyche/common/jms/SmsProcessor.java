package com.azkj.noopsyche.common.jms;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.controller.OrderController;
import com.azkj.noopsyche.service.Impl.OrderServiceImpl;
import com.azkj.noopsyche.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import java.text.ParseException;
import java.util.Map;

@Component
public class SmsProcessor {

    @Autowired
    private  JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    @Qualifier("orderServiceImpl")
    private OrderServiceImpl orderService;
    public void sendSmsToQueue(Destination destination, String message) {
        jmsMessagingTemplate.convertAndSend(destination,message);
    }

    @JmsListener(destination = "aaa")
    public  void  aaa(String text)  {

    }
}
