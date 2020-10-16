package com.wombats.mspipelinemetrobus.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Edgar Quiroz
 * @version 16/10/20 1.0.0
 * DTO de ubicaciones de metrobus
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UbicacionMetrobus {

	private Long id;
	private Long unidadId;
	private String unidadEtiqueta;
	private Integer unidadEstatus;
	private String ubicacionLatitud;
	private String ubicacionLongitud;
	private LocalDateTime fechaActualizacion;
	private Alcaldia alcaldia;
	
}
