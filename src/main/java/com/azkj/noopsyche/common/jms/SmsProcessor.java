package com.azkj.noopsyche.common.jms;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.annotation.JmsListeners;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

@Component
public class SmsProcessor {

    @Autowired
    private JmsTemplate jmsTemplate;


    public void sendSmsToQueue(Destination destination, String message) {
        jmsTemplate.convertAndSend(destination,message);
    }


    @JmsListener(destination = "aaa")
    public  void  aaa(String text){
        System.out.println(text);
    }
}
