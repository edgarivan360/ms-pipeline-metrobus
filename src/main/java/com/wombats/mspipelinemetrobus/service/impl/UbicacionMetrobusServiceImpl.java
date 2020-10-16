package com.wombats.mspipelinemetrobus.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wombats.mspipelinemetrobus.dto.Alcaldia;
import com.wombats.mspipelinemetrobus.dto.UbicacionMetrobusApi;
import com.wombats.mspipelinemetrobus.entity.UbicacionMetrobusEntity;
import com.wombats.mspipelinemetrobus.mapper.UbicacionMetrobusMapper;
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
	private UbicacionMetrobusEntityRepository ubicacionMetrobusEntityRepository;
	
	@Autowired
	private IAlcaldiaService alcaldiaService;
	
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
}
