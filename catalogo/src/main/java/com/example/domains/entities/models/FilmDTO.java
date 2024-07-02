package com.example.domains.entities.models;

import java.io.Serializable;
import java.math.BigDecimal;

import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilmDTO implements Serializable{
	@JsonProperty("id")
	private int filmId;
	@JsonProperty("titulo")
	private String titulo;
	@JsonProperty("language")
	private Language language;
	@JsonProperty("rentalDuration")
	private byte rentalDuration;
	@JsonProperty("rentalRate")
	private BigDecimal rentalRate;
	@JsonProperty("replacementCost")
	private BigDecimal replacementCost;

	public static FilmDTO from(Film source) {
		return new FilmDTO(source.getFilmId(), source.getTitle(), source.getLanguage(), source.getRentalDuration(), source.getRentalRate(), source.getReplacementCost());
	}
	
	public static Film from(FilmDTO source) {
		return new Film(source.getFilmId(), source.getTitulo(), source.getLanguage(), source.getRentalDuration(), source.getRentalRate(), source.getReplacementCost());
	}

}
