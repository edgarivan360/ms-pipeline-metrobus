CREATE TABLE alcaldia_coordenadas (
	id BIGINT(20) NOT NULL AUTO_INCREMENT,
	latlng VARCHAR(100) NOT NULL,
	alcaldia_id	BIGINT(20) NOT NULL,
	PRIMARY KEY (id),
	INDEX idx_alcaldia_coordenadas_alcaldia_id (alcaldia_id),
	CONSTRAINT fk_alcaldia_coordenadas_alcaldia_id FOREIGN KEY (alcaldia_id) REFERENCES alcaldia (id) ON UPDATE CASCADE ON DELETE CASCADE
);