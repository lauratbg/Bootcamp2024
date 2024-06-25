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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Actor modify(Actor item) throws NotFoundException, InvalidDataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Actor item) throws InvalidDataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void repartePremios() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
