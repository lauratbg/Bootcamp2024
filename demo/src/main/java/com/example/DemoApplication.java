package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.domains.contracts.repositories.ActorRepository;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	ActorRepository dao;

	@Override
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
		dao.findByActorIdGreaterThanEqual(200).forEach(System.out::println);
		dao.findByJPQL(200).forEach(System.out::println);
		dao.findBySQL(200).forEach(System.out::println);
		dao.findAll((root, query, builder) -> builder.greaterThanOrEqualTo(root.get("actorId"), 200));

	}

}
