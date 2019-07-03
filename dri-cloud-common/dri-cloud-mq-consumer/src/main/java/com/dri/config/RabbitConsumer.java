package com.dri.config;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.dri.entity.Order;

@Component
public class RabbitConsumer {

	@RabbitListener(queues = "OpeLogQueue")
	@RabbitHandler
	public void consume(@Payload Order order) {
		System.out.println("----收到消息，开始消费-----");
		System.out.println("d订单id：" + order.toString());

		System.out.println("--------消费完成--------");
	}

//	@RabbitListener(queues="OpeLogQueue"
//			  bindings = @QueueBinding( //数据是否持久化 value = @Queue(value =
//			  "OpeLogQueue",durable ="true"), exchange = @Exchange(name =
//			 "dri.opeLogEx",durable = "true",type = "direct"), key="OpeLogQueueKey")
//					)
//	@RabbitHandler
//	public void consume2(@Payload Order order) {
//				 System.out.println("----收到消息，开始消费-----");
//			        System.out.println("d订单id："+order.toString());
//			        //Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
//
//			        
//			        //channel.basicAck(deliveryTag,false);
//			        System.out.println("--------消费完成--------");
//			}
}
