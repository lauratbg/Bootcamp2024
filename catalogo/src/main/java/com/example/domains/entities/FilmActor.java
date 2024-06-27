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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * The persistent class for the film_actor database table.
 * 
 */
@Entity
@Table(name = "film_actor")
@NamedQuery(name = "FilmActor.findAll", query = "SELECT f FROM FilmActor f")
public class FilmActor extends EntityBase<FilmActor> implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@Positive
	@Size(max = 5)
	private FilmActorPK id;

	@Column(name = "last_update", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@PastOrPresent
	private Timestamp lastUpdate;

	// bi-directional many-to-one association to Actor
	@ManyToOne
	@JoinColumn(name = "actor_id", nullable = false, insertable = false, updatable = false)
	@JsonManagedReference
	private Actor actor;

	// bi-directional many-to-one association to Film
	@ManyToOne
	@JoinColumn(name = "film_id", nullable = false, insertable = false, updatable = false)
	@JsonManagedReference
	private Film film;

	public FilmActor() {
	}

	public FilmActor(FilmActorPK id) {
		super();
		this.id = id;
	}

	public FilmActor(Actor actor, Film film) {
		this.actor = actor;
		this.film = film;
	}

	public FilmActorPK getId() {
		return this.id;
	}

	public void setId(FilmActorPK id) {
		this.id = id;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Actor getActor() {
		return this.actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
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
		FilmActor other = (FilmActor) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "FilmActor [id=" + id + ", actor=" + actor + "]";
	}

	@PrePersist
	void prePersiste() {
		if (id == null) {
			setId(new FilmActorPK(film.getFilmId(), actor.getActorId()));
		}

	}
}