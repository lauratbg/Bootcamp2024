package com.example.domains.contracts.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.domains.entities.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer>, JpaSpecificationExecutor<Actor> {
	List<Actor> findTop5ByLastNameStartingWithOrderByFirstNameDesc(String prefijo);
	List<Actor> findTop5ByLastNameStartingWith(String prefijo, Sort orderBy);
	
	List<Actor> findByActorIdGreaterThanEqual(int actorId);
	
	@Query(value = "FROM Actor a WHERE a.actorId >= ?1")
	List<Actor> findByJPQL(int actorId);
	
	@Query(value = "SELECT * FROM actor WHERE actor_id >= :id", nativeQuery = true)
	List<Actor> findBySQL(int id);
	

}