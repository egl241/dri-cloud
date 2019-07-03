package com.dri.mq.test;

import java.util.UUID;

import org.json.JSONObject;
import org.json.JSONStringer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dri.config.RabbitSender;
import com.dri.entity.Order;
import com.rabbitmq.tools.json.JSONUtil;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SimpleTest {
	
	
	@Autowired
	private RabbitSender rabbitSender;
	
	@Test
	public void test() {
		
		Order order = new Order();
        order.setId(2018092102);
        order.setName("测试订单2");
        order.setMessageId(System.currentTimeMillis()+"$"+UUID.randomUUID().toString());
        
        
        try {
			rabbitSender.sendOrder(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //orderService.createOrder(order);
	}

}
