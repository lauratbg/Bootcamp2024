package com.example.domains.entities.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.domains.entities.Film;
import com.example.domains.entities.Language;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Schema(name = "Pelicula (Editar)", description = "Version editable de las películas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmEditDTO {
	private int filmId;
	private String description;
	private Integer length;
//	@Pattern(regexp = "^(G|PG|PG-13|R|NC-17)$")
	private String rating;
	private Short releaseYear;
	@NotNull
	private Byte rentalDuration;
	@NotNull
	private BigDecimal rentalRate;
	@NotNull
	private BigDecimal replacementCost;
	@NotBlank
	@Size(min = 2, max = 128)
	private String title;
	@NotNull
	private Integer languageId;
	private Integer languageVOId;
	private List<Integer> actors = new ArrayList<Integer>();
	private List<Integer> categories = new ArrayList<Integer>();

	public static FilmEditDTO from(Film source) {
		return new FilmEditDTO(source.getFilmId(), source.getDescription(), source.getLength(),
				source.getRating() == null ? null : source.getRating().getValue(), source.getReleaseYear(),
				source.getRentalDuration(), source.getRentalRate(), source.getReplacementCost(), source.getTitle(),
				source.getLanguage() == null ? null : source.getLanguage().getLanguageId(),
				source.getLanguageVO() == null ? null : source.getLanguageVO().getLanguageId(),
				source.getActors().stream().map(item -> item.getActorId()).collect(Collectors.toList()),
				source.getCategories().stream().map(item -> item.getCategoryId()).collect(Collectors.toList()));
	}

	public static Film from(FilmEditDTO source) {
		Film rslt = new Film(source.getFilmId(), source.getTitle(), source.getDescription(), source.getReleaseYear(),
				source.getLanguageId() == null ? null : new Language(source.getLanguageId()),
				source.getLanguageVOId() == null ? null : new Language(source.getLanguageVOId()),
				source.getRentalDuration(), source.getRentalRate(), source.getLength(), source.getReplacementCost(),
				source.getRating() == null ? null : Film.Rating.getEnum(source.getRating()));
		source.getActors().stream().forEach(item -> rslt.addActor(item));
		source.getCategories().stream().forEach(item -> rslt.addCategory(item));
		return rslt;
	}

}
