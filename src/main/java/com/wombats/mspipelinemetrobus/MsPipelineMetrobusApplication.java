package com.wombats.mspipelinemetrobus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MsPipelineMetrobusApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPipelineMetrobusApplication.class, args);
	}

}
