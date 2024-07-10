package com.bjj.detect.gptest;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class ListenTest implements ApplicationListener<ListenerEvent> {

	@Override
	public void onApplicationEvent(ListenerEvent event) {
		System.out.println("--------------------");
		System.out.println("---------Listener-----------");
		System.out.println("--------------------");
	}
}
