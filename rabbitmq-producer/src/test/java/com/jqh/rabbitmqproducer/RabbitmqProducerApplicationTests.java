package com.jqh.rabbitmqproducer;

import com.jqh.rabbitmqproducer.entity.Order;
import com.jqh.rabbitmqproducer.producer.OrderSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqProducerApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private OrderSender orderSender ;

	@Test
	public void testSend1() throws Exception{
		Order order = new Order();
		order.setId("2018092201-1");
		order.setName("这是测试订单1");
		order.setMessageId(System.currentTimeMillis()+"$"+ UUID.randomUUID().toString());
		orderSender.send(order);
	}

}
