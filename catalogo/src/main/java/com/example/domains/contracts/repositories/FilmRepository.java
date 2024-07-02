package com.example.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import com.example.domains.core.contracts.repositories.ProjectionsAndSpecificationJpaRepository;
import com.example.domains.entities.Film;

public interface FilmRepository extends ProjectionsAndSpecificationJpaRepository<Film, Integer>{

	// con esto consigo un repositorio con proyecciones, ordenación, paginación, posibilidad
	// de meter especificaciones...

	List<Film> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
}
