package com.bjj.detect;

import com.alibaba.fastjson.JSON;
import com.bjj.detect.entity.PgNotice;
import org.apache.commons.lang3.builder.ToStringExclude;
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
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

@SpringBootTest
public class RocketMQTest {
	private AtomicInteger at = new AtomicInteger(0);
	private CountDownLatch count = new CountDownLatch(10000);

	@Test
	public void testMax() throws InterruptedException, MQClientException, RemotingException {
		DefaultMQProducer producer = new DefaultMQProducer("AsyncProducer");
		producer.setNamesrvAddr("121.43.97.203:9876");
		producer.setVipChannelEnabled(false);
		producer.start();

		for (int i = 0; i < 10000; i++) {
			final int index = i;
			PgNotice notice = new PgNotice();
			Message message = new Message("Simple1","tagA",(JSON.toJSONString(notice)).getBytes(StandardCharsets.UTF_8));
			message.setKeys(String.valueOf(notice.getId()));
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
		count.await(5000,TimeUnit.MILLISECONDS);
		producer.shutdown();
		System.out.println(count);
	}

	@Test
	public void testConsume() throws MQClientException, InterruptedException {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("SimpleConsumer");
		consumer.setNamesrvAddr("121.43.97.203:9876");
		consumer.subscribe("Simple1","*");
		TokenBucket bucket = new TokenBucket(100,70);
		// 设置顺序消息监听器
		consumer.registerMessageListener(new MessageListenerConcurrently() {
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
				try{
					for (MessageExt msg:list){
//						if (bucket.tryConsume()){
							String result = new String(msg.getBody(),"UTF-8");
							PgNotice notice = JSON.parseObject(result,PgNotice.class);
							at.incrementAndGet();
//						pgNoticeDao.insert(notice);
							System.out.println(Thread.currentThread().getName()+":"+msg.getKeys());
//						}
					}
				}catch (Exception e){

				}
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});
//		consumer.setPullInterval(100);
//		consumer.setPullBatchSize(7);
//		consumer.setConsumeThreadMax(1);
//		consumer.setConsumeThreadMin(1);
		consumer.start();
		Long start = System.currentTimeMillis();
		count.await(5000,TimeUnit.MILLISECONDS);
		consumer.shutdown();
		Long end = System.currentTimeMillis();
		System.out.println("time:"+(end-start)+",count:"+at);
	}

	public class TokenBucket {
		private final AtomicInteger tokens;
		private final int maxTokens;
		private final int refillRate;
		private final ScheduledExecutorService scheduler;

		public TokenBucket(int maxTokens, int refillRate) {
			this.maxTokens = maxTokens;
			this.refillRate = refillRate;
			this.tokens = new AtomicInteger(maxTokens);
			this.scheduler = Executors.newSingleThreadScheduledExecutor();
			startRefillTask();
		}

		private void startRefillTask() {
			scheduler.scheduleAtFixedRate(() -> {
				if (tokens.get() < maxTokens) {
					tokens.addAndGet(refillRate);
					if (tokens.get() > maxTokens) {
						tokens.set(maxTokens);
					}
				}
			}, 0, 1000, TimeUnit.MILLISECONDS);
		}

		public boolean tryConsume() {
			if (tokens.get() > 0) {
				tokens.decrementAndGet();
				return true;
			}
			return false;
		}

		public void shutdown() {
			scheduler.shutdown();
			try {
				if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
					scheduler.shutdownNow();
				}
			} catch (InterruptedException e) {
				scheduler.shutdownNow();
			}
		}
	}

//	private class TokenBucket {
//		// 当前令牌数量
//		private AtomicLong tokens;
//		// 令牌桶的容量
//		private final int capacity;
//		// 上一次令牌发放时间
//		private AtomicLong lastTokenTime;
//		// 令牌生成速度（每秒）
//		private final int rate;
//		// 锁，用于控制令牌数量的修改
//		private final ReentrantLock lock;
//
//		public TokenBucket(int capacity, int rate) {
//			this.capacity = capacity;
//			this.rate = rate;
//			this.tokens = new AtomicLong(rate);
//			this.lastTokenTime = new AtomicLong(System.currentTimeMillis());
//			this.lock = new ReentrantLock(true);
//			// 初始化定时任务，填充令牌
//			ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
//			scheduledExecutorService.scheduleAtFixedRate(() -> {
//				refillTokens();
//			}, 0, 1000 / rate, TimeUnit.MILLISECONDS);
//		}
//
//		private void refillTokens() {
//			long now = System.currentTimeMillis();
//			long timeSinceLastToken = now - lastTokenTime.get();
//			if (timeSinceLastToken > 0) {
//				long newTokens = timeSinceLastToken * rate / 1000;
//				long currentTokens = tokens.addAndGet(newTokens);
//				if (currentTokens > capacity) {
//					tokens.set(capacity);
//				}
//				lastTokenTime.set(now);
//			}
//		}
//
//		public boolean tryAcquire() {
//			lock.lock();
//			try {
//				long now = System.currentTimeMillis();
//				long timeSinceLastToken = now - lastTokenTime.get();
//				refillTokens(); // 根据时间差重新填充令牌
//				if (tokens.get() > 0) {
//					tokens.decrementAndGet();
//					return true;
//				} else {
//					return false;
//				}
//			} finally {
//				lock.unlock();
//			}
//		}
//	}
}
