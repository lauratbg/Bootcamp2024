package com.example.domains.entities.models;

import org.springframework.beans.factory.annotation.Value;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;

@Schema(name = "Actor (Corto)", description = "Version corta de los actores")
public interface ActorShort {
	@Value("#{target.ActorId}")
	@Schema(description = "Identificador del actor", accessMode = AccessMode.READ_ONLY)
	int getActorId();
	
	@Value("#{target.firstName + ' ' + target.lastName}")
	@Schema(description = "Nombre y apellidos del actor")
	String getNombre();
}
