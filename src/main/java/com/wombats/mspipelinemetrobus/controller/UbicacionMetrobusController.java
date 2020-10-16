package com.wombats.mspipelinemetrobus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wombats.mspipelinemetrobus.dto.UbicacionMetrobus;
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
	
	/**
	 * End Point que realiza la búsqueda de ubicaciones de metrobus por estatus
	 */
	@GetMapping("/{unidadEstatus}/estatus")
	public List<UbicacionMetrobus> obtenerUbicacionesMetrobusPorEstatus(@PathVariable("unidadEstatus") Integer unidadEstatus) {
		return ubicacionMetrobusService.obtenerUbicacionesMetrobusPorEstatus(unidadEstatus);
	}
	
	/**
	 * End Point que realiza la búsqueda de ubicaciones de metrobus por unidad ID
	 */
	@GetMapping("/{unidadId}/unidad")
	public List<UbicacionMetrobus> obtenerUbicacionesMetrobusPorUnidadId(@PathVariable("unidadId") Long unidadId) {
		return ubicacionMetrobusService.obtenerUbicacionesMetrobusPorUnidadId(unidadId);
	}
	
	/**
	 * End Point que realiza la búsqueda de ubicaciones de metrobus por alcaldía ID
	 */
	@GetMapping("/{alcaldiaId}/alcaldia")
	public List<UbicacionMetrobus> obtenerUbicacionesMetrobusPorAlcaldiaId(@PathVariable("alcaldiaId") Long alcaldiaId) {
		return ubicacionMetrobusService.obtenerUbicacionesMetrobusPorAlcaldiaId(alcaldiaId);
	}

}
