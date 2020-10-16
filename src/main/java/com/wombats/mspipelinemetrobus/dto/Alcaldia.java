package com.wombats.mspipelinemetrobus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Edgar Quiroz
 * @version 16/10/20 1.0.0
 * DTO de alcaldías
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Alcaldia {

	private Long id;
	private String nombre;
	
}
