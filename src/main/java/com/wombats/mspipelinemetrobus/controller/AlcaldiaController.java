package com.wombats.mspipelinemetrobus.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wombats.mspipelinemetrobus.dto.Alcaldia;
import com.wombats.mspipelinemetrobus.service.IAlcaldiaService;

/**
 * @author Edgar Quiroz
 * @version 16/10/20 1.0.0
 * Controlador REST de alcaldías
 */
@RestController
@RequestMapping("v1/alcaldia")
public class AlcaldiaController {
	
	/**
	 * Beans utilizados por el controlador
	 */
	@Autowired
	private IAlcaldiaService alcaldiaService;
	
	/**
	 * End Point que realiza la carga de alcaldías CDMX
	 */
	@PostMapping("/carga/masiva")
	public void cargarAlcaldias() {
		alcaldiaService.cargarAlcaldias();
	}
	
	/**
	 * End Point para la obtener alcaldía asociada a un punto (coordenada)
	 * @param Latitud del punto a buscar
	 * @param Longitud del punto a buscar
	 * @return Optional de una alcaldía, vacío si no encuentra coincidencias
	 */
	@GetMapping("/coordenadas")
	public Optional<Alcaldia> obtenerAlcaldiaPorCoordenadas(@RequestParam("latitud") String latitud,
			@RequestParam("longitud") String longitud) {
		return alcaldiaService.obtenerAlcaldiaPorCoordenadas(latitud, longitud);
	}

}
