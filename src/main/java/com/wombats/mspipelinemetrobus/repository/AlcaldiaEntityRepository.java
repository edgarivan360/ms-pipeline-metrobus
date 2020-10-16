package com.wombats.mspipelinemetrobus.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
	
	@Modifying
	@Query(value = "DELETE FROM alcaldia WHERE 1 = 1", nativeQuery = true)
	void deleteAll();
	
	@Modifying
	@Query(value = "INSERT INTO alcaldia (id, nombre, region) VALUES (?1, ?2, ST_GEOMFROMTEXT(?3))", nativeQuery = true)
	void save(Long id, String nombre, String regionPuntos);
	
	@Query(value = "SELECT a.id, a.nombre " + 
			"FROM alcaldia a " + 
			"WHERE ST_CONTAINS(a.region, POINT(?1, ?2)) ", nativeQuery = true)
	Optional<AlcaldiaEntityProjection> findProjectionByCoordenadas(String latitud, String longitud);
	
	@Query(value = "SELECT a.id, a.nombre " + 
			"FROM alcaldia a " + 
			"WHERE a.id IN ?1 ", nativeQuery = true)
	List<AlcaldiaEntityProjection> findAllProjectionByIdIn(Set<Long> id);
	
	@Query(value = "SELECT a.id, a.nombre " + 
			"FROM alcaldia a ", nativeQuery = true)
	List<AlcaldiaEntityProjection> findAllProjection();
	
}