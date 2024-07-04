package com.example.domains.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import com.example.domains.core.entities.EntityBase;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Clase persistente para la tabla de actores en la base de datos.
 */
@Entity
@Table(name="actor")
@NamedQuery(name="Actor.findAll", query="SELECT a FROM Actor a")
@Schema(description = "Entidad que representa un Actor en la base de datos")
public class Actor extends EntityBase<Actor> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="actor_id", unique=true, nullable=false)
	@Positive
	@Schema(description = "Identificador único del Actor", example = "1", required = true, accessMode = AccessMode.READ_ONLY)
	private int actorId;

	@Column(name="first_name", nullable=false, length=45)
	@NotBlank
	@Size(max=45, min=2)
	@Schema(description = "Nombre del Actor", example = "John", required = true)
	private String firstName;

	@Column(name="last_name", nullable=false, length=45)
	@NotBlank
	@Size(max=45, min=2)
	@Schema(description = "Apellido del Actor", example = "Doe", required = true)
	private String lastName;

	@Column(name="last_update", insertable=false, updatable=false, nullable=false)
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@PastOrPresent
	@Schema(description = "Última actualización del Actor", example = "2023-07-04 12:34:56", required = true, accessMode = AccessMode.READ_ONLY)
	private Timestamp lastUpdate;

	// Relación bidireccional muchos-a-uno con FilmActor
	@OneToMany(mappedBy="actor", fetch = FetchType.LAZY)
	@JsonBackReference
	@Schema(description = "Lista de asociaciones FilmActor relacionadas con el Actor")
	private List<FilmActor> filmActors;

	public Actor() {
	}
	
	public Actor(int actorId) {
		this.actorId = actorId;
	}
	
	public Actor(@Positive int actorId, String firstName, String lastName) {
		this.actorId = actorId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public int getActorId() {
		return this.actorId;
	}

	public void setActorId(int actorId) {
		this.actorId = actorId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public List<FilmActor> getFilmActors() {
		return this.filmActors;
	}

	public void setFilmActors(List<FilmActor> filmActors) {
		this.filmActors = filmActors;
	}

	public FilmActor addFilmActor(FilmActor filmActor) {
		getFilmActors().add(filmActor);
		filmActor.setActor(this);

		return filmActor;
	}

	public FilmActor removeFilmActor(FilmActor filmActor) {
		getFilmActors().remove(filmActor);
		filmActor.setActor(null);

		return filmActor;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(actorId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Actor other = (Actor) obj;
		return actorId == other.actorId;
	}

	@Override
	public String toString() {
		return "Actor [actorId=" + actorId + ", firstName=" + firstName + ", lastName=" + lastName + ", lastUpdate="
				+ lastUpdate + "]";
	}
	
	public void jubilate() {
		
	}
	
	public void recibePremio(String premio) {
		
	}
}
