package com.bjj.detect;

import com.bjj.detect.gptest.AnnoTest;
import com.bjj.detect.gptest.ListenerEvent;
import com.bjj.detect.gptest.OtherTest;
import com.bjj.detect.gptest.RestTest;
import com.bjj.detect.service.AuthorityProvider;
import com.bjj.detect.serviceimpl.AuthorityProviderImpl;
import com.bjj.detect.util.SqlConnect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.metrics.StartupStep;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@SpringBootTest
public class ConsoleTest {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Test
	public void go(){
		ListenerEvent listenerEvent = new ListenerEvent(this);
		listenerEvent.setMessage("测试");
//		publisher.publishEvent(listenerEvent);
//		otherTest.setName("aaa");
	}

	@Autowired
	private SqlConnect sqlConnect;

	@Test
	public void testAspect(){
		sqlConnect.connect();
	}
}
