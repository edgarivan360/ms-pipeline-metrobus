package com.wombats.mspipelinemetrobus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * @author Edgar Quiroz
 * @version 16/10/20 1.0.0
 * Clase de ejecución principal
 */
@SpringBootApplication
@EnableScheduling
public class MsPipelineMetrobusApplication {

	/**
	 * Método de ejecución principal
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(MsPipelineMetrobusApplication.class, args);
	}
	
	/**
	 * Beans genéricos 
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
