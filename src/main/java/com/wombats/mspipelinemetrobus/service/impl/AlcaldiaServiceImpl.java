package com.wombats.mspipelinemetrobus.service.impl;

import lombok.extern.java.Log;

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
import com.wombats.mspipelinemetrobus.dto.AlcaldiaApi;
import com.wombats.mspipelinemetrobus.mapper.AlcaldiaMapper;
import com.wombats.mspipelinemetrobus.projection.AlcaldiaEntityProjection;
import com.wombats.mspipelinemetrobus.repository.AlcaldiaEntityRepository;
import com.wombats.mspipelinemetrobus.service.IAlcaldiaService;

/**
 * @author Edgar Quiroz
 * @version 16/10/20 1.0.0
 * Servicio para manipulación de alcaldías
 */
@Log
@Service
public class AlcaldiaServiceImpl implements IAlcaldiaService {
	
	/**
	 * Valores fijos en application.yml 
	 */
	@Value("${datos-abiertos-cdmx.api-url}")
	private String apiUrl;
	
	@Value("${datos-abiertos-cdmx.api-dataset.alcaldias}")
	private String apiDataset;
	
	@Value("${datos-abiertos-cdmx.max-rows.alcaldias}")
	private Integer maxRows;
	
	/**
	 * Beans utilizados por el servicio 
	 */
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private AlcaldiaEntityRepository alcaldiaEntityRepository;
	
	/**
	 * Método que realiza el drop&create de las alcaldías y su información espacial desde la API
	 * de los datos abiertos de la CDMX
	 */
	@Override
	@Transactional
	public void cargarAlcaldias() {
		// Se eliminan todas las alcaldías existentes
		alcaldiaEntityRepository.deleteAll();
		
		// Se obtiene la información de todas las alcaldías en la API de los datos abiertos de la CDMX
		AlcaldiaApi alcaldiaApi = 
				restTemplate.exchange(apiUrl + apiDataset + "&rows={maxRows}",
						HttpMethod.GET, null, new ParameterizedTypeReference<AlcaldiaApi>() {}, maxRows).getBody();
		
		// Se itera cada delegación obtenida por la API
		alcaldiaApi.getRecords().forEach(alcaldia -> {
			// Se guardan la lista de coordenadas por cada delegación
			List<List<Double>> coordenadas = alcaldia.getFields().getGeoShape().getCoordinates().get(0);
			
			// Se crea una lista con las coordenadas con el siguiente formato:
			// ["x1 y1", "x2 y2", ... , "xn yn"]
			List<String> coordenadasList = coordenadas.stream().map(coordenada -> 
				new StringBuilder(String.valueOf(coordenada.get(1)))
					.append(" ").append(coordenada.get(0)).toString()
			).collect(Collectors.toList());
			
			// Se guardan los valores clave de la entidad alcaldía (id, nombre, coordenadas)
			Long alcaldiaId = Long.parseLong(alcaldia.getFields().getMunicipio());
			String alcaldiaNombre = alcaldia.getFields().getNomgeo();
			
			// Las coordenadas se guardan con el formato POLYGON para crearlas con formato espacial
			String regionPuntos = "POLYGON((" + coordenadasList.stream().collect(Collectors.joining(", ")) + "))";
			
			// Se guarda la alcaldía en la BD
			log.info("Guardando coordenadas de la alcaldía: " + alcaldiaNombre + "...");
			alcaldiaEntityRepository.save(alcaldiaId, alcaldiaNombre, regionPuntos);
		});
	}

	/**
	 * Método para la obtener alcaldía asociada a un punto (coordenada)
	 * @param Latitud del punto a buscar
	 * @param Longitud del punto a buscar
	 * @return Optional de una alcaldía, vacío si no encuentra coincidencias
	 */
	@Override
	public Optional<Alcaldia> obtenerAlcaldiaPorCoordenadas(String latitud, String longitud) {
		// Se obtiene la alcaldía en forma de proyección SQL asociada a los valores de las coodenadas
		Optional<AlcaldiaEntityProjection> alcaldiaEntityProjection = 
				alcaldiaEntityRepository.findByCoordenadas(latitud, longitud);
		
		// Si se obtuvieron coincidencias (alcaldía existente)
		// Se retorna mediante una clase mapper, un DTO con el id y el nombre de la alcaldía en cuestión
		if (alcaldiaEntityProjection.isPresent()) {
			return Optional.ofNullable(AlcaldiaMapper.toDto(alcaldiaEntityProjection.get()));
		}
		
		// Si no se obtuvieron coincidencias (alcaldía inexistente), se retorna vacío
		return Optional.empty();
	}
	
	/**
	 * Método para contar las alcaldías existentes en la BD
	 * @return Número de alcaldías almacenadas en la BD
	 */
	@Override
	public Long contarAlcaldias() {
		return alcaldiaEntityRepository.count();
	}

}
