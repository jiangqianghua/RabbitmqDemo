package com.jqh.rabbitmqproducer.producer;

import com.jqh.rabbitmqproducer.entity.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderSender {

    @Autowired
    private RabbitTemplate  rabbitTemplate ;

    public void send(Order order){
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(order.getMessageId());
        rabbitTemplate.convertAndSend("order-exchange", // exchange
                "order.abcd", // routingkey    exchange 和routingkey 需要去控制台创建
                order,    // 消息内容
                correlationData  //  消息唯一id
                );
    }
}
