package com.jk.controller;

import com.jk.model.News;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by asus on 2018/5/7.
 */
@Component
@RabbitListener(queues = "1369")
public class ThreadController {

    /*@RabbitHandler
    public void process(News news){
        System.out.println(news);
    }*/

}
