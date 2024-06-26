package com.example.domains.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

import com.example.domains.core.entities.EntityBase;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;


/**
 * The persistent class for the film_category database table.
 * 
 */
@Entity
@Table(name="film_category")
@NamedQuery(name="FilmCategory.findAll", query="SELECT f FROM FilmCategory f")
public class FilmCategory extends EntityBase<FilmCategory> implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@Positive
	private FilmCategoryPK id;

	@Column(name="last_update", nullable=false)
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@PastOrPresent
	private Timestamp lastUpdate;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="category_id", nullable=false, insertable=false, updatable=false)
	@JsonManagedReference
	@NotNull
	private Category category;

	//bi-directional many-to-one association to Film
	@ManyToOne
	@JoinColumn(name="film_id", nullable=false, insertable=false, updatable=false)
	@JsonManagedReference
	@NotNull
	private Film film;

	public FilmCategory() {
	}

	public FilmCategory(FilmCategoryPK id) {
		this.id = id;
	}

	public FilmCategory(Category category, Film film) {
		this.category = category;
		this.film = film;
	}


	public FilmCategoryPK getId() {
		return this.id;
	}

	public void setId(FilmCategoryPK id) {
		this.id = id;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Film getFilm() {
		return this.film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FilmCategory other = (FilmCategory) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "FilmCategory [id=" + id + ", category=" + category + ", film=" + film + "]";
	}
	
	

}