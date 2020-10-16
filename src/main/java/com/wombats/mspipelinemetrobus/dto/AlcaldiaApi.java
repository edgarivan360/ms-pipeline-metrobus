package com.wombats.mspipelinemetrobus.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Edgar Quiroz
 * @version 16/10/20 1.0.0
 * DTO de alcaldías de la API de los datos abiertos de la CDMX
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"nhits",
"parameters",
"records"
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlcaldiaApi {

	@JsonProperty("nhits")
	private Integer nhits;
	@JsonProperty("parameters")
	private Parameters parameters;
	@JsonProperty("records")
	private List<Record> records;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({
	"dataset",
	"timezone",
	"rows",
	"start",
	"format"
	})
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Parameters {

		@JsonProperty("dataset")
		private String dataset;
		@JsonProperty("timezone")
		private String timezone;
		@JsonProperty("rows")
		private Integer rows;
		@JsonProperty("start")
		private Integer start;
		@JsonProperty("format")
		private String format;

	}
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({
	"datasetid",
	"recordid",
	"fields",
	"geometry",
	"record_timestamp"
	})
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Record {

		@JsonProperty("datasetid")
		private String datasetid;
		@JsonProperty("recordid")
		private String recordid;
		@JsonProperty("fields")
		private Fields fields;
		@JsonProperty("geometry")
		private Geometry geometry;
		@JsonProperty("record_timestamp")
		private String recordTimestamp;

	}
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({
	"geo_point_2d",
	"cve_mun",
	"cvegeo",
	"geo_shape",
	"cve_ent",
	"nomgeo",
	"municipio"
	})
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Fields {

		@JsonProperty("geo_point_2d")
		private List<Double> geoPoint2d;
		@JsonProperty("cve_mun")
		private String cveMun;
		@JsonProperty("cvegeo")
		private String cvegeo;
		@JsonProperty("geo_shape")
		private GeoShape geoShape;
		@JsonProperty("cve_ent")
		private String cveEnt;
		@JsonProperty("nomgeo")
		private String nomgeo;
		@JsonProperty("municipio")
		private String municipio;

	}
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({
	"type",
	"coordinates"
	})
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class GeoShape {

		@JsonProperty("type")
		private String type;
		@JsonProperty("coordinates")
		private List<List<List<Double>>> coordinates;

	}
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({
	"type",
	"coordinates"
	})
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Geometry {

		@JsonProperty("type")
		private String type;
		@JsonProperty("coordinates")
		private List<Double> coordinates;

	}

}