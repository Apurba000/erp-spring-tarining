package com.brainstation23.erp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class ErpApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ErpApplication.class, args);

//		for (String name : context.getBeanDefinitionNames()){
//			log.info("Bean : " + name);
//		}
	}

}
