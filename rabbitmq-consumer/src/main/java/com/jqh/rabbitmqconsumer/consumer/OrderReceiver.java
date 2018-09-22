package com.jqh.rabbitmqconsumer.consumer;

import com.jqh.rabbitmqproducer.entity.Order;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderReceiver {

    // 中调，rabbitmq监听服务那些消息队列
    // 该注解很强大，会在rabbitmq管控里面添加exchange 和 queue对应关系
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "order-queue" , durable = "true"),
            exchange = @Exchange(name="order-exchange" , durable = "true" , type = "topic"),
            key = "order.#"
        )
    )
    @RabbitHandler
    public void onOrderMessage(@Payload Order order, @Headers Map<String,Object> headers, Channel channel) throws Exception{
        // 消费者操作
        System.out.println("-------收到消息----------------");
        System.out.println("订单id:"+order.getId());

        Long deliverTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        //  手动签收,如果不签收，下次启动项目，依然会收到该消息，在手动签收的情况下
        channel.basicAck(deliverTag,false);
    }
}
