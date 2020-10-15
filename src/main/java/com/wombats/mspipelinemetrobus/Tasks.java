package com.wombats.mspipelinemetrobus;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Tasks {

	@Autowired
	private PruebaController pruebaController;
	
	private static final Logger LOG = LoggerFactory.getLogger(Tasks.class);
	
	@Scheduled(fixedRate = 5000)
	public void logPrueba() {
		LOG.info("Ejecucion: " + pruebaController.helloWolrd());
	}

}