package com.wombats.mspipelinemetrobus.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wombats.mspipelinemetrobus.dto.Alcaldia;
import com.wombats.mspipelinemetrobus.dto.UbicacionMetrobus;
import com.wombats.mspipelinemetrobus.dto.UbicacionMetrobusApi;
import com.wombats.mspipelinemetrobus.entity.UbicacionMetrobusEntity;
import com.wombats.mspipelinemetrobus.mapper.UbicacionMetrobusMapper;
import com.wombats.mspipelinemetrobus.projection.AlcaldiaEntityProjection;
import com.wombats.mspipelinemetrobus.repository.UbicacionMetrobusEntityRepository;
import com.wombats.mspipelinemetrobus.service.IAlcaldiaService;
import com.wombats.mspipelinemetrobus.service.IUbicacionMetrobusService;

/**
 * @author Edgar Quiroz
 * @version 16/10/20 1.0.0
 * Servicio para manipulación de ubicaciones de metrobus
 */
@Service
public class UbicacionMetrobusServiceImpl implements IUbicacionMetrobusService {

	/**
	 * Valores fijos en application.yml 
	 */
	@Value("${datos-abiertos-cdmx.api-url}")
	private String apiUrl;
	
	@Value("${datos-abiertos-cdmx.api-dataset.ubicaciones-metrobus}")
	private String apiDataset;
	
	@Value("${datos-abiertos-cdmx.max-rows.ubicaciones-metrobus}")
	private Integer maxRows;
	
	/**
	 * Beans utilizados por el servicio 
	 */
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private IAlcaldiaService alcaldiaService;
	
	@Autowired
	private UbicacionMetrobusEntityRepository ubicacionMetrobusEntityRepository;
	
	/**
	 * Método que realiza la carga de ubicaciones de metrobus mediante la API
	 * de los datos abiertos de la CDMX
	 */
	@Override
	@Transactional
	public void cargarUbicacionesMetrobus() {
		
		// Se obtiene la información de todas las ubicaciones de metrobus en la última hora
		// en la API de los datos abiertos de la CDMX
		UbicacionMetrobusApi ubicacionMetrobusApi = restTemplate.exchange(apiUrl + apiDataset + "&rows={maxRows}",
				HttpMethod.GET, null, new ParameterizedTypeReference<UbicacionMetrobusApi>() {}, maxRows).getBody();
		
		// Se itera cada ubicación obtenida por la API
		List<UbicacionMetrobusEntity> ubicacionMetrobusEntityList = ubicacionMetrobusApi.getRecords()
				.stream().map(ubicacionMetrobus -> {
					// Se guardan los valores de latitud y longitud por cada registro (ubicación)
					String latitud = String.valueOf(ubicacionMetrobus.getFields().getPositionLatitude());
					String longitud = String.valueOf(ubicacionMetrobus.getFields().getPositionLongitude());
					
					// Se obtiene la alcaldía asociada a dicha latitud y longitud
					Optional<Alcaldia> alcaldia = alcaldiaService.obtenerAlcaldiaPorCoordenadas(latitud, longitud);
					
					// Se retorna la entidad de ubicaciones de metrobus mediante un mapper,
					// la cual, es guardada en una lista para su posterior almacenamiento en la BD
					return UbicacionMetrobusMapper
							.toEntity(ubicacionMetrobus.getFields(), alcaldia.isPresent() ? alcaldia.get().getId() : null);
				}).collect(Collectors.toList());
		
		// Se guarda la lista de ubicaciones de metrobus en la BD
		ubicacionMetrobusEntityRepository.saveAll(ubicacionMetrobusEntityList);
	}
	
	/**
	 * Método para devolver un set de alcaldía IDs
	 * @param Lista de entidades de ubicaciones de metrobus
	 * @return Set de alcaldía IDs
	 */
	@Override
	public Set<Long> obtenerAlcaldiaIds(List<UbicacionMetrobusEntity> ubicacionMetrobusEntityList) {	
		// Se genera un set con todos los IDs de las alcaldías asociadas a la ubicaciones de metrobus encontradas
		return ubicacionMetrobusEntityList.stream()
				.filter(ubicacionMetrobusEntity -> Optional.ofNullable(ubicacionMetrobusEntity.getAlcaldiaId()).isPresent())
				.map(UbicacionMetrobusEntity::getAlcaldiaId)
				.collect(Collectors.toSet());
	}
	
	/**
	 * Método para devolver unidades de metrobus
	 * @param Lista de entidades de ubicaciones de metrobus
	 * @return Lista de DTO de ubicaciones de metrobus
	 */
	@Override
	public List<UbicacionMetrobus> obtenerUbicacionesMetrobus(List<UbicacionMetrobusEntity> ubicacionMetrobusEntityList) {	
		// Se genera una lista con todos los IDs de las alcaldías asociadas a la ubicaciones de metrobus encontradas
		Set<Long> alcaldiaIds = obtenerAlcaldiaIds(ubicacionMetrobusEntityList);
				
		// Se genera un map con todos las alcaldías encontradas para evitar realizar una consulta por cada iteración
		Map<Long, AlcaldiaEntityProjection> alcaldiaMap = alcaldiaService.obtenerAlcaldiasMap(alcaldiaIds);
				
		// Se iteran las ubicaciones de metrobus para devolver su respectivo DTO
		return ubicacionMetrobusEntityList.stream()
				.map(ubicacionMetrobusEntity -> {
					// Se guarda el ID de la alcaldía asociada a cada registro
					// y se genera un objeto vació de la proyección SQL de las alcaldias
					Long alcaldiaId = ubicacionMetrobusEntity.getAlcaldiaId();
					Optional<AlcaldiaEntityProjection> alcaldiaEntityProjection = Optional.empty();
					
					// Si la ubicación de metrobus tiene registrado un ID de alcaldía
					// y dicho ID está contenido en el map de las alcaldías
					// Se settea la proyección SQL con la alcaldía correspondiente en el map
					if (Optional.ofNullable(alcaldiaId).isPresent() && alcaldiaMap.containsKey(alcaldiaId)) {
						alcaldiaEntityProjection = Optional.ofNullable(alcaldiaMap.get(alcaldiaId));
					}
					
					// Se devuelve el DTO de las ubicaciones de metrobus
					return UbicacionMetrobusMapper.toDto(ubicacionMetrobusEntity, alcaldiaEntityProjection);
				}).collect(Collectors.toList());
	}

	/**
	 * Método para devolver unidades de metrobus por estatus
	 * @param Identificador del estatus a buscar
	 * @return Lista de DTO de ubicaciones de metrobus
	 */
	@Override
	public List<UbicacionMetrobus> obtenerUbicacionesMetrobusPorEstatus(Integer unidadEstatus) {
		// Se buscan todas las ubicaciones metrobus por unidadEstatus
		return obtenerUbicacionesMetrobus(
				ubicacionMetrobusEntityRepository.findAllByUnidadEstatus(unidadEstatus));
		
	}

	/**
	 * Método para devolver unidades de metrobus por ID de unidad
	 * @param Identificador de la unidad a buscar
	 * @return Lista de DTO de ubicaciones de metrobus
	 */
	@Override
	public List<UbicacionMetrobus> obtenerUbicacionesMetrobusPorUnidadId(Long unidadId) {
		// Se buscan todas las ubicaciones metrobus por unidadEstatus
		return obtenerUbicacionesMetrobus(
				ubicacionMetrobusEntityRepository.findAllByUnidadId(unidadId));
		
	}

	/**
	 * Método para devolver unidades de metrobus por ID de alcaldía
	 * @param Identificador de la alcaldía a buscar
	 * @return Lista de DTO de ubicaciones de metrobus
	 */
	@Override
	public List<UbicacionMetrobus> obtenerUbicacionesMetrobusPorAlcaldiaId(Long alcaldiaId) {
		// Se buscan todas las ubicaciones metrobus por alcaldiaId
		return obtenerUbicacionesMetrobus(
				ubicacionMetrobusEntityRepository.findAllByAlcaldiaId(alcaldiaId));
	}
	
}
