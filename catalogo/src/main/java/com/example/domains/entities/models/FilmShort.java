package com.example.domains.entities.models;

import java.io.Serializable;

import com.example.domains.entities.Film;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "Pelicula (Corto)", description = "Version corta de las peliculas")
public class FilmShort implements Serializable{
	@JsonProperty("id")
	@Schema(description = "Identificador de la pelicula", accessMode = AccessMode.READ_ONLY)
	private int filmId;
	@JsonProperty("titulo")
	@Schema(description = "Titulo de la pelicula")
	private String titulo;

	public static FilmShort from(Film source) {
		return new FilmShort(source.getFilmId(), source.getTitle());
	}

	public static Film from(FilmShort source) {
		return new Film(source.getFilmId(), source.getTitulo());
	}

}