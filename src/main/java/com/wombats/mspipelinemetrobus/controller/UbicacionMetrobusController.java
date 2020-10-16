package com.wombats.mspipelinemetrobus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wombats.mspipelinemetrobus.service.IUbicacionMetrobusService;

/**
 * @author Edgar Quiroz
 * @version 16/10/20 1.0.0
 * Controlador REST de ubicaciones de metrobus
 */
@RestController
@RequestMapping("v1/ubicacion/metrobus")
public class UbicacionMetrobusController {
	
	/**
	 * Beans utilizados por el servicio 
	 */
	@Autowired
	private IUbicacionMetrobusService ubicacionMetrobusService;
	
	/**
	 * End Point que realiza la carga de ubicaciones de metrobus
	 */
	@PostMapping("/carga/masiva")
	public void cargarUbicacionesMetrobus() {
		ubicacionMetrobusService.cargarUbicacionesMetrobus();
	}

}
