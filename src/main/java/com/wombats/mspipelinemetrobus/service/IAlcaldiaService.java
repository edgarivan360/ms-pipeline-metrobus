package com.wombats.mspipelinemetrobus.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.wombats.mspipelinemetrobus.dto.Alcaldia;
import com.wombats.mspipelinemetrobus.projection.AlcaldiaEntityProjection;

/**
 * @author Edgar Quiroz
 * @version 16/10/20 1.0.0
 * Interfaz de definición de métodos para manipulación de alcaldías
 */
@Service
public interface IAlcaldiaService {
	
	void cargarAlcaldias();
	
	public Optional<Alcaldia> obtenerAlcaldiaPorCoordenadas(String latitud, String longitud);
	
	public Map<Long, AlcaldiaEntityProjection> obtenerAlcaldiasMap(Set<Long> alcaldiaIds);
	
	public List<Alcaldia> obtenerAlcaldias();
	
	public List<Alcaldia> obtenerAlcaldiasDisponibles();

}
