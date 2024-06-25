package com.example.domains.contracts.services;

import java.security.Timestamp;
import java.util.List;

import com.example.domains.core.contracts.services.SpecificationDomainService;
import com.example.domains.entities.Film;

public interface FilmService extends SpecificationDomainService<Film, Integer> {
	List<Film> novedades(Timestamp fecha);

}
