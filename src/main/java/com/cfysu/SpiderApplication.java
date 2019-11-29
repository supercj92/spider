package com.cfysu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

@SpringBootApplication
public class SpiderApplication implements ApplicationListener<ContextStartedEvent>{

	public static void main(String[] args) {
		SpringApplication.run(SpiderApplication.class, args);
	}



	@Override
	public void onApplicationEvent(ContextStartedEvent contextStartedEvent) {
		System.out.println("onApplicationEvent");
	}

}
