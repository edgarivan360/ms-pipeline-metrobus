CREATE TABLE ubicacion_metrobus (
	id BIGINT(20) NOT NULL AUTO_INCREMENT,
	alcaldia_id BIGINT(20) NULL,
	fecha_actualizacion DATETIME NOT NULL,
	ubicacion_latitud VARCHAR(50) NOT NULL,
	ubicacion_longitud VARCHAR(50) NOT NULL,
	unidad_estatus INT NOT NULL,
	unidad_etiqueta VARCHAR(15) NOT NULL,
	unidad_id BIGINT(20) NOT NULL,
	PRIMARY KEY (id),
	INDEX idx_ubicacion_metrobus_alcaldia_id (alcaldia_id),
	CONSTRAINT fk_ubicacion_metrobus_alcaldia_id FOREIGN KEY (alcaldia_id) REFERENCES alcaldia (id) ON UPDATE RESTRICT ON DELETE RESTRICT
);