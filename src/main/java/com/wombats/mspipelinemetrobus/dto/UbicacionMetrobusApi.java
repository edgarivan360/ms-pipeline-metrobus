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
 * DTO de ubicaciones de metrobus de la API de los datos abiertos de la CDMX
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
public class UbicacionMetrobusApi {

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
	"vehicle_id",
	"trip_start_date",
	"date_updated",
	"position_longitude",
	"trip_schedule_relationship",
	"position_speed",
	"position_latitude",
	"trip_route_id",
	"vehicle_label",
	"position_odometer",
	"trip_id",
	"vehicle_current_status",
	"geographic_point"
	})
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Fields {

		@JsonProperty("vehicle_id")
		private String vehicleId;
		@JsonProperty("trip_start_date")
		private String tripStartDate;
		@JsonProperty("date_updated")
		private String dateUpdated;
		@JsonProperty("position_longitude")
		private Double positionLongitude;
		@JsonProperty("trip_schedule_relationship")
		private Integer tripScheduleRelationship;
		@JsonProperty("position_speed")
		private Integer positionSpeed;
		@JsonProperty("position_latitude")
		private Double positionLatitude;
		@JsonProperty("trip_route_id")
		private String tripRouteId;
		@JsonProperty("vehicle_label")
		private String vehicleLabel;
		@JsonProperty("position_odometer")
		private Integer positionOdometer;
		@JsonProperty("trip_id")
		private String tripId;
		@JsonProperty("vehicle_current_status")
		private Integer vehicleCurrentStatus;
		@JsonProperty("geographic_point")
		private List<Double> geographicPoint;

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