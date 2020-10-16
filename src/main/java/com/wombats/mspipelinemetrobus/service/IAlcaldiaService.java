package com.wombats.mspipelinemetrobus.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wombats.mspipelinemetrobus.dto.Alcaldia;

/**
 * @author Edgar Quiroz
 * @version 16/10/20 1.0.0
 * Interfaz de definición de métodos para manipulación de alcaldías
 */
@Service
public interface IAlcaldiaService {
	
	void cargarAlcaldias();
	
	public Optional<Alcaldia> obtenerAlcaldiaPorCoordenadas(String latitud, String longitud);
	
	public Long contarAlcaldias();

}
