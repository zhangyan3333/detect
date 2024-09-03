package com.bjj.detect.gptest;

import com.bjj.detect.entity.PgNotice;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class OtherTest {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("SimpleConsumer");
		consumer.setNamesrvAddr("121.43.97.203:9876");
		consumer.subscribe("Simple","*");

		// 设置顺序消息监听器
		consumer.registerMessageListener(new MessageListenerConcurrently() {
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
				try{
					for (MessageExt msg:list){
						String result = new String(msg.getBody(),"UTF-8");
						PgNotice notice = new PgNotice();
						System.out.println(Thread.currentThread().getName());
					}
				}catch (Exception e){

				}
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});

		consumer.start();


		DefaultMQProducer producer = new DefaultMQProducer("AsyncProducer");
		producer.setNamesrvAddr("121.43.97.203:9876");
		producer.setVipChannelEnabled(false);
		producer.start();
		CountDownLatch count = new CountDownLatch(10000);
		for (int i = 0; i < 10000; i++) {
			final int index = i;

			Message message = new Message("Simple","tagA",(i + "AsyncProducer").getBytes(StandardCharsets.UTF_8));
			producer.send(message, new SendCallback() {
				@Override
				public void onSuccess(SendResult sendResult) {
					count.countDown();
//					System.out.println(index+"消息发送成功"+sendResult);
				}

				@Override
				public void onException(Throwable throwable) {
					count.countDown();
					System.out.println(index+"消息发送失败"+throwable.getStackTrace());
				}
			});
		}
		count.await(50000, TimeUnit.MILLISECONDS);



		producer.shutdown();
		consumer.shutdown();
	}
}
