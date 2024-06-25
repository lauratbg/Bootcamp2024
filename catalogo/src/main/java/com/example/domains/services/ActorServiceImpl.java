package com.example.domains.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@Service
public class ActorServiceImpl implements ActorService {
	private ActorRepository daoActorRepository;

	public ActorServiceImpl(ActorRepository daoActorRepository) {
		this.daoActorRepository = daoActorRepository;
	}

	@Override
	public <T> List<T> getByProjection(Class<T> type) {
		return daoActorRepository.findAllBy(type);
	}

	@Override
	public <T> Iterable<T> getByProjection(Sort sort, Class<T> type) {
		return daoActorRepository.findAllBy(sort, type);
	}

	@Override
	public <T> Page<T> getByProjection(Pageable pageable, Class<T> type) {
		return daoActorRepository.findAllBy(pageable, type);
	}

	@Override
	public Iterable<Actor> getAll(Sort sort) {
		return daoActorRepository.findAll(sort);
	}

	@Override
	public Page<Actor> getAll(Pageable pageable) {
		return daoActorRepository.findAll(pageable);
	}

	@Override
	public List<Actor> getAll() {
		return daoActorRepository.findAll();
	}

	@Override
	public Optional<Actor> getOne(Integer id) {
		return daoActorRepository.findById(id);
	}

	@Override
	public Actor add(Actor item) throws DuplicateKeyException, InvalidDataException {
		if (item == null)
			throw new InvalidDataException("No puede ser nulo");
		if (item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		// evito que haga el exist si no es necesario
		if (item.getActorId() != 0 && daoActorRepository.existsById(item.getActorId()))
			throw new DuplicateKeyException("Ya existe");
		return daoActorRepository.save(item);
	}

	@Override
	public Actor modify(Actor item) throws NotFoundException, InvalidDataException {
		if (item == null)
			throw new InvalidDataException("No puede ser nulo");
		if (item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		if (!daoActorRepository.existsById(item.getActorId()))
			throw new NotFoundException();
		return daoActorRepository.save(item);
	}

	@Override
	public void delete(Actor item) throws InvalidDataException {
		if (item == null)
			throw new InvalidDataException("No puede ser nulo");
		daoActorRepository.delete(item);
	}

	@Override
	public void deleteById(Integer id) {
		daoActorRepository.deleteById(id);

	}

	@Override
	public void repartePremios() {
		System.out.println("Repartiendo premios...");

	}

}
