package com.wombats.mspipelinemetrobus.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.BeanUtils;

import com.wombats.mspipelinemetrobus.dto.UbicacionMetrobus;
import com.wombats.mspipelinemetrobus.dto.UbicacionMetrobusApi.Fields;
import com.wombats.mspipelinemetrobus.entity.UbicacionMetrobusEntity;
import com.wombats.mspipelinemetrobus.projection.AlcaldiaEntityProjection;

/**
 * @author Edgar Quiroz
 * @version 16/10/20 1.0.0
 * Clase para  mappear DTO y entidades asociadas a ubicaciones de metrobus
 */
public final class UbicacionMetrobusMapper {
	
	/**
	 * Constructor de la clase 
	 */
	public UbicacionMetrobusMapper() {}
	
	/**
	 * Método para generar una entidad de ubicaciones de metrobus
	 * @param Datos obtenidos de la API para una ubicación de metrobus
	 * @param Identificador de la alcaldía asociado a los datos de la ubicación del metrobus
	 * @return Entidad de ubicaciones de metrobus
	 */
	public static UbicacionMetrobusEntity toEntity(Fields ubicacionMetrobus, Long alcaldiaId) {
		LocalDateTime fechaActualizacion = LocalDateTime.parse(ubicacionMetrobus.getDateUpdated(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		return UbicacionMetrobusEntity.builder()
				.alcaldiaId(alcaldiaId)
				.unidadId(Long.parseLong(ubicacionMetrobus.getVehicleId()))
				.unidadEtiqueta(ubicacionMetrobus.getVehicleLabel())
				.unidadEstatus(ubicacionMetrobus.getVehicleCurrentStatus())
				.ubicacionLatitud(String.valueOf(ubicacionMetrobus.getPositionLatitude()))
				.ubicacionLongitud(String.valueOf(ubicacionMetrobus.getPositionLongitude()))
				.fechaActualizacion(fechaActualizacion)
				.build();
	}
	
	/**
	 * Método para generar un DTO de ubicaciones de metrobus
	 * @param Entidad de ubicaciones de metrobus
	 * @param Proyección SQL de la alcaldía asociada a los datos de la ubicación del metrobus
	 * @return DTO de ubicaciones de metrobus
	 */
	public static UbicacionMetrobus toDto(UbicacionMetrobusEntity ubicacionMetrobusEntity, Optional<AlcaldiaEntityProjection> alcaldiaEntityProjection) {
		UbicacionMetrobus ubicacionMetrobus = UbicacionMetrobus.builder().build();
		BeanUtils.copyProperties(ubicacionMetrobusEntity, ubicacionMetrobus);
		
		if (alcaldiaEntityProjection.isPresent()) {
			ubicacionMetrobus.setAlcaldia(AlcaldiaMapper.toDto(alcaldiaEntityProjection.get()));
		}
		
		return ubicacionMetrobus;
	}

}
