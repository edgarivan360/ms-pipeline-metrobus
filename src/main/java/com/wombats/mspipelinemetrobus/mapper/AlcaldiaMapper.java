package com.wombats.mspipelinemetrobus.mapper;

import com.wombats.mspipelinemetrobus.dto.Alcaldia;
import com.wombats.mspipelinemetrobus.entity.AlcaldiaEntity;
import com.wombats.mspipelinemetrobus.projection.AlcaldiaEntityProjection;

/**
 * @author Edgar Quiroz
 * @version 16/10/20 1.0.0
 * Clase para  mappear DTO y entidades asociadas a alcaldías
 */
public final class AlcaldiaMapper {
	
	/**
	 * Constructor de la clase 
	 */
	public AlcaldiaMapper() {}
	
	/**
	 * Método para generar una entidad de alcaldía
	 * @param Identificador de la alcaldía
	 * @param Nombre de la alcaldía
	 * @return Entidad de alcaldía
	 */
	public static AlcaldiaEntity toEntity(String id, String nombre) {
		return AlcaldiaEntity.builder()
				.id(Long.parseLong(id))
				.nombre(nombre)
				.build();
	}
	
	/**
	 * Método para generar un DTO de alcaldía
	 * @param Proyección SQL de la entidad alcaldía
	 * @return DTO de alcaldía
	 */
	public static Alcaldia toDto(AlcaldiaEntityProjection alcaldiaEntityProjection) {
		return Alcaldia.builder()
				.id(alcaldiaEntityProjection.getId())
				.nombre(alcaldiaEntityProjection.getNombre())
				.build();
	}

}
