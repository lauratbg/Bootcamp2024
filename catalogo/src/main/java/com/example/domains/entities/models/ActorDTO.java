package com.example.domains.entities.models;

import java.io.Serializable;

import com.example.domains.entities.Actor;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "Actor", description = "Versión de actores con nombre y apellidos por separado")
public class ActorDTO implements Serializable {
	@JsonProperty("id")
	@Schema(description = "Identificador del actor", accessMode = AccessMode.READ_ONLY)
	
	private int actorId;
	@JsonProperty("nombre")
	@Schema(description = "Nombre del actor")
	@Size(min=2, max = 45)
	private String firstName;
	@JsonProperty("apellidos")
	@Size(min=2, max = 45)
	@Schema(description = "Apellidos del actor")
	private String lastName;

	public static ActorDTO from(Actor source) {
		return new ActorDTO(source.getActorId(), source.getFirstName(), source.getLastName());
	}
	
	public static Actor from(ActorDTO source) {
		return new Actor(source.getActorId(), source.getFirstName(), source.getLastName());
	}
}
