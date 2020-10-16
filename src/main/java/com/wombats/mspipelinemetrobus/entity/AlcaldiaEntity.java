package com.wombats.mspipelinemetrobus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.vividsolutions.jts.geom.Polygon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Edgar Quiroz
 * @version 16/10/20 1.0.0
 * Entidad alcaldía
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "alcaldia")
public class AlcaldiaEntity {
	
	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "region", columnDefinition = "POLYGON")
	private Polygon region;
	
}
