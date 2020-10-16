package com.wombats.mspipelinemetrobus.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.wombats.mspipelinemetrobus.dto.UbicacionMetrobus;
import com.wombats.mspipelinemetrobus.entity.UbicacionMetrobusEntity;

/**
 * @author Edgar Quiroz
 * @version 16/10/20 1.0.0
 * Interfaz de definición de métodos para manipulación de ubicaciones de metrobus
 */
@Service
public interface IUbicacionMetrobusService {
	
	public void cargarUbicacionesMetrobus();
	
	public Set<Long> obtenerAlcaldiaIds(List<UbicacionMetrobusEntity> ubicacionMetrobusEntityList);
	
	public List<UbicacionMetrobus> obtenerUbicacionesMetrobus(List<UbicacionMetrobusEntity> ubicacionMetrobusEntityList);
	
	public List<UbicacionMetrobus> obtenerUbicacionesMetrobusPorEstatus(Integer unidadEstatus);
	
	public List<UbicacionMetrobus> obtenerUbicacionesMetrobusPorUnidadId(Long unidadId);
	
	public List<UbicacionMetrobus> obtenerUbicacionesMetrobusPorAlcaldiaId(Long alcaldiaId);
	
}
