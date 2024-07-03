package com.example.domains.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Film;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import io.micrometer.common.lang.NonNull;

@Service
public class FilmServiceImpl implements FilmService {
	private FilmRepository dao;

	public FilmServiceImpl(FilmRepository dao) {
		this.dao = dao;
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);

	}


	@Override
	public List<Film> getAll(Specification<Film> spec) {
		return dao.findAll(spec);
	}

	@Override
	public Page<Film> getAll(Specification<Film> spec, Pageable pageable) {
		return dao.findAll(spec, pageable);
	}

	@Override
	public List<Film> getAll(Specification<Film> spec, Sort sort) {
		return dao.findAll(spec, sort);
	}

	@Override
	public Iterable<Film> getAll(Sort sort) {
		return dao.findAll(sort);
	}

	@Override
	public Page<Film> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<Film> getAll() {
		return dao.findAll();
	}

	@Override
	public Optional<Film> getOne(Integer id) {
		return dao.findById(id);
	}

	@Override
	public Film add(Film item) throws DuplicateKeyException, InvalidDataException {
		if (item == null)
			throw new InvalidDataException("No puede ser nulo");
//		if(item.isInvalid())
//			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		if (item.getFilmId() != 0 && dao.existsById(item.getFilmId()))
			throw new DuplicateKeyException("Ya existe");
		return dao.save(item);
	}

	@Override
	public Film modify(Film item) throws NotFoundException, InvalidDataException {
		if (item == null)
			throw new InvalidDataException("No puede ser nulo");
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		var leido = dao.findById(item.getFilmId()).orElseThrow(()-> new NotFoundException());
		return dao.save(item.merge(leido));
	}

	@Override
	public void delete(Film item) throws InvalidDataException {
		if (item == null)
			throw new InvalidDataException("No puede ser nulo");
		dao.delete(item);

	}


	@Override
	public Optional<Film> getOne(Specification<Film> spec) {
		return dao.findOne(spec);
	}



	@Override
	public List<Film> novedades(@NonNull java.sql.Timestamp fecha) {
		return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha);

	}


}
