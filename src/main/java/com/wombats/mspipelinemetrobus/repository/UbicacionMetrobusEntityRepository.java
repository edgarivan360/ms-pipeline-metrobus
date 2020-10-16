package com.wombats.mspipelinemetrobus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wombats.mspipelinemetrobus.entity.UbicacionMetrobusEntity;

/**
 * @author Edgar Quiroz
 * @version 16/10/20 1.0.0
 * Repositorio para manipulación de BD de ubicaciones de metrobus
 */
@Repository
public interface UbicacionMetrobusEntityRepository extends JpaRepository<UbicacionMetrobusEntity, Long> {
	
	List<UbicacionMetrobusEntity> findAllByUnidadEstatus(Integer unidadEstatus);
	
	List<UbicacionMetrobusEntity> findAllByUnidadId(Long unidadId);
	
	List<UbicacionMetrobusEntity> findAllByAlcaldiaId(Long alcaldiaId);
	
}