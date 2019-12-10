package com.azkj.noopsyche.common.jms;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

@Component
public class SmsProcessor {

    @Autowired
    private  JmsMessagingTemplate jmsMessagingTemplate;


    public void sendSmsToQueue(Destination destination, String message) {
        jmsMessagingTemplate.convertAndSend(destination,message);
    }

    @JmsListener(destination = "aaa")
    public  void  aaa(String text){
        System.out.println(text);
    }
}
