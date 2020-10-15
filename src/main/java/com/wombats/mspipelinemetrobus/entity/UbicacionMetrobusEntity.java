package com.wombats.mspipelinemetrobus.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ubicacion_metrobus")
public class UbicacionMetrobusEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "unidad_id")
	private Long unidadId;
	
	@Column(name = "unidad_etiqueta")
	private String unidadEtiqueta;
	
	@Column(name = "unidad_estatus")
	private Integer unidadEstatus;
	
	@Column(name = "ubicacion_latitud")
	private String ubicacionLatitud;
	
	@Column(name = "ubicacion_longitud")
	private String ubicacionLongitud;
	
	@Column(name = "fecha_actualizacion")
	private LocalDateTime fechaActualizacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "alcaldia_id", referencedColumnName = "id")
	private AlcaldiaEntity alcaldia;
}
