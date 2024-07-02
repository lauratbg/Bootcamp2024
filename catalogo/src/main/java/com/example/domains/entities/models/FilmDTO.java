package com.example.domains.entities.models;

import java.io.Serializable;

import com.example.domains.entities.Film;
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

	public static FilmDTO from(Film source) {
		return new FilmDTO(source.getFilmId(), source.getTitle());
	}
	
	public static Film from(FilmDTO source) {
		return new Film(source.getFilmId(), source.getTitulo());
	}

}
