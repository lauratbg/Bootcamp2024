package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.example.domains.contracts.repositories.ActorRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	ActorRepository dao;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada...");

//		var item = dao.findById(204);
//
//		if (item.isEmpty())
//			System.err.println("No encontrado");
//		else {
//			var actor = item.get();
//			actor.setFirstName(actor.getFirstName().toUpperCase());
//			dao.save(actor);
//		}

//		dao.deleteById(206);

//		dao.findAll().forEach(System.out::println);

//		dao.findTop5ByLastNameStartingWithOrderByFirstNameDesc("P").forEach(System.out::println);
//		dao.findTop5ByLastNameStartingWith("P", Sort.by("LastName").ascending()).forEach(System.out::println);
//		dao.findByActorIdGreaterThanEqual(200).forEach(System.out::println);
//		dao.findByJPQL(200).forEach(System.out::println);
//		dao.findBySQL(200).forEach(System.out::println);
//		dao.findAll((root, query, builder) -> builder.greaterThanOrEqualTo(root.get("actorId"), 200)).forEach(System.out::println);
//		dao.findAll((root, query, builder) -> builder.lessThan(root.get("actorId"), 10)).forEach(System.out::println);

//		var item = dao.findById(1);
//
//		if (item.isEmpty())
//			System.err.println("No encontrado");
//		else {
//			var actor = item.get();
//			System.out.println(actor);
//			actor.getFilmActors().forEach(f -> System.out.println(f.getFilm().getTitle()));
//		}

//		var actor = new Actor(0, "  ", null);
//		if(actor.isValid()) System.out.println(dao.save(actor));
//		else actor.getErrors().forEach(System.out::println);

		// Proyecciones
//		dao.findAll().forEach(item -> System.out.println(ActorDTO.from(item)));

//		var actor = new ActorDTO(0, "FROM", "DTO");
//		dao.save(ActorDTO.from(actor));
//		dao.findAll().forEach(item -> System.out.println(ActorDTO.from(item)));

		// me devuelve actorDTO
//		dao.readByActorIdGreaterThanEqual(200).forEach(System.out::println);
//		
//		// me devuelve actorShort
//		dao.queryByActorIdGreaterThanEqual(200).forEach(item -> System.out.println(item.getActorId() + " " + item.getNombre()));

		// usando la genérica para que saque DTOs
//		dao.findByActorIdGreaterThanEqual(200, ActorDTO.class).forEach(System.out::println);

		// Pageable
//		dao.findAll(PageRequest.of(3, 10, Sort.by("ActorId"))).get().forEach(System.out::println);

		// Serialización
//		var serialize = new ObjectMapper();
//		dao.findByActorIdGreaterThanEqual(200, ActorDTO.class).forEach(item -> {
//			try {
//				System.out.println(serialize.writeValueAsString(item));
//			} catch (JsonProcessingException e) {
//				e.printStackTrace();
//			}
//		});
		
		// No se puede serializar Actor porque tiene la colección de FilmActor que 
		// tiene dentro Actor -> bucle infinito
		// 			SOLUCIÓN 1
		// Se puede solucionar poniendo @JsonIgnore en el atributo lista de FilmActor de Actor
		// 			SOLUCIÓN 2
		// o también poniendo en la lista @JsonBackReference y en el atributo Actor
		// de filmactor @JsonManagedReference, para decir cual es la parte uno y cual la parte n

//		var serialize = new ObjectMapper();
//		dao.findAll(PageRequest.of(3, 10, Sort.by("ActorId"))).forEach(item -> {
//			try {
//				System.out.println(serialize.writeValueAsString(item));
//			} catch (JsonProcessingException e) {
//				e.printStackTrace();
//			}
//		});
		
		// Serializador XML
		var serialize = new XmlMapper();
		dao.findAll(PageRequest.of(3, 10, Sort.by("ActorId"))).forEach(item -> {
			try {
				System.out.println(serialize.writeValueAsString(item));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		});
	}

}
