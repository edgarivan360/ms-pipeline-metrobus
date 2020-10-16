package com.wombats.mspipelinemetrobus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wombats.mspipelinemetrobus.service.IUbicacionMetrobusService;

import lombok.extern.java.Log;

/**
 * @author Edgar Quiroz
 * @version 16/10/20 1.0.0
 * Scheduler de carga de ubicaciones de metrobus
 */
@Log
@Component
public class MsPipelineMetrobusScheduler {
	
	/**
	 * Beans utilizados por el scheduler 
	 */
	@Autowired
	private IUbicacionMetrobusService ubicacionMetrobusService;
	
	/**
	 * Scheduler que ejecuta la creación de ubicaciones de metrobus cada hora del día con 5 minutos
	 * Ej, 01:05, 02:05, 09:05, 14:05, 17:05, 20:05, 00:05
	 */
	@Scheduled(cron = "0 5 * * * ?")
	public void logPrueba() {
		log.info("Creando ubicaciones metrobus...");
		ubicacionMetrobusService.cargarUbicacionesMetrobus();
		log.info("Creación ubicaciones metrobus finalizada...");
	}

}