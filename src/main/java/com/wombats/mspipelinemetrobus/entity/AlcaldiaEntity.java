package com.wombats.mspipelinemetrobus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "alcaldia")
public class AlcaldiaEntity {
	
	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "nombre")
	private String nombre;
	
}
