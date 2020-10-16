package com.wombats.mspipelinemetrobus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wombats.mspipelinemetrobus.entity.AlcaldiaEntity;
import com.wombats.mspipelinemetrobus.projection.AlcaldiaEntityProjection;

/**
 * @author Edgar Quiroz
 * @version 16/10/20 1.0.0
 * Repositorio para manipulación de BD de alcaldías
 */
@Repository
public interface AlcaldiaEntityRepository extends JpaRepository<AlcaldiaEntity, Long> {
	
	@Override
	@Modifying
	@Query(value = "DELETE FROM alcaldia WHERE 1 = 1", nativeQuery = true)
	void deleteAll();
	
	@Modifying
	@Query(value = "INSERT INTO alcaldia (id, nombre, region) VALUES (?1, ?2, ST_GEOMFROMTEXT(?3))", nativeQuery = true)
	void save(Long id, String nombre, String regionPuntos);
	
	@Query(value = "SELECT a.id, a.nombre " + 
			"FROM alcaldia a " + 
			"WHERE ST_CONTAINS(a.region, POINT(?1, ?2)) ", nativeQuery = true)
	Optional<AlcaldiaEntityProjection> findByCoordenadas(String latitud, String longitud);
	
}