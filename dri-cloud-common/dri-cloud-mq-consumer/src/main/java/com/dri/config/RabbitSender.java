package com.dri.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dri.entity.Order;

@Component
public class RabbitSender {

	@Autowired
	//private RabbitTemplate rabbitTemplate;
	private AmqpTemplate rabbitTemplate;
	
	
	
	public void sendOrder(Order order) throws Exception {
        // 通过实现 ConfirmCallback 接口，消息发送到 Broker 后触发回调，确认消息是否到达 Broker 服务器，也就是只确认是否正确到达 Exchange 中
       // rabbitTemplate.setConfirmCallback(confirmCallback);
        //消息唯一ID
        //CorrelationData correlationData = new CorrelationData(order.getMessageId());
        //rabbitTemplate.convertAndSend("dri.opeLogEx", "OpeLogQueueKey", order, correlationData);
		
        rabbitTemplate.convertAndSend("dri.opeLogEx","OpeLogQueueKey", order);
//        rabbitTemplate.convertAndSend("OpeLogQueueKey", order);
    } 
}
