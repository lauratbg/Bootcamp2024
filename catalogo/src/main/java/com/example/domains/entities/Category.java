package com.example.domains.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import com.example.domains.core.entities.EntityBase;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
 * The persistent class for the category database table.
 * 
 */
@Entity
@Table(name="category")
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category extends EntityBase<Category> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="category_id", unique=true, nullable=false)
	@JsonProperty("id")
	@Positive
	private int categoryId;

	@Column(name="last_update", insertable=false, updatable=false, nullable=false)
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@PastOrPresent
	private Timestamp lastUpdate;

	@Column(nullable=false, length=25)
	@NotBlank
	@Size(max=25, min=2)
	@JsonProperty("categoría")
	private String name;

	//bi-directional many-to-one association to FilmCategory
	@OneToMany(mappedBy="category")
	@JsonIgnore
	@JsonBackReference
	private List<FilmCategory> filmCategories;

	public Category() {
	}
	
	public Category(int categoryId, String name) {
		this.categoryId = categoryId;
		this.name = name;
	}
	
	public Category(int categoryId, String name, List<FilmCategory> filmCategories) {
		this.categoryId = categoryId;
		this.name = name;
		this.filmCategories = filmCategories;
	}

	public Category(int categoryId) {
		this.categoryId = categoryId;
	}


	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FilmCategory> getFilmCategories() {
		return this.filmCategories;
	}

	public void setFilmCategories(List<FilmCategory> filmCategories) {
		this.filmCategories = filmCategories;
	}

	public FilmCategory addFilmCategory(FilmCategory filmCategory) {
		getFilmCategories().add(filmCategory);
		filmCategory.setCategory(this);

		return filmCategory;
	}

	public FilmCategory removeFilmCategory(FilmCategory filmCategory) {
		getFilmCategories().remove(filmCategory);
		filmCategory.setCategory(null);

		return filmCategory;
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoryId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return categoryId == other.categoryId;
	}

	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", name=" + name +"]";
	}
	
	

}