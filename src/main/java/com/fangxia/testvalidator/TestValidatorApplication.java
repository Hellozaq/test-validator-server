package com.fangxia.testvalidator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource("classpath:quartz/quartz_jobs.xml")
@SpringBootApplication
public class TestValidatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestValidatorApplication.class, args);
	}

}
