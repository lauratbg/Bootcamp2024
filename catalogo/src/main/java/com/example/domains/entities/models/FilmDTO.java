package com.example.domains.entities.models;

import java.io.Serializable;
import java.math.BigDecimal;

import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilmDTO implements Serializable{
	@Schema(description = "Identificador de la película", accessMode = AccessMode.READ_ONLY)
	@JsonProperty("id")
	private int filmId;
	@JsonProperty("titulo")
	@Schema(description = "El título de la película")
	@NotBlank
	@Size(min=2, max = 128)
	private String titulo;
	@Schema(description = "El idioma de la película")
	@NotNull
	@JsonProperty("language")
	private Language language;
	@Schema(description = "La duración del período de alquiler, en días", minimum = "0", exclusiveMinimum = true)
	@JsonProperty("rentalDuration")
	private byte rentalDuration;
	@JsonProperty("rentalRate")
	@Schema(description = "El coste de alquilar la película por el período establecido", minimum = "0", exclusiveMinimum = true)
	private BigDecimal rentalRate;
	@Schema(description = "El importe cobrado al cliente si la película no se devuelve o se devuelve en un estado dañado", minimum = "0", exclusiveMinimum = true)
	@JsonProperty("replacementCost")
	private BigDecimal replacementCost;

	public static FilmDTO from(Film source) {
		return new FilmDTO(source.getFilmId(), source.getTitle(), source.getLanguage(), source.getRentalDuration(), source.getRentalRate(), source.getReplacementCost());
	}
	
	public static Film from(FilmDTO source) {
		return new Film(source.getFilmId(), source.getTitulo(), source.getLanguage(), source.getRentalDuration(), source.getRentalRate(), source.getReplacementCost());
	}

}
