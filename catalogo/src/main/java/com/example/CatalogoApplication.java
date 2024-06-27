package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.contracts.services.ActorService;
import com.example.domains.contracts.services.CategoryService;
import com.example.domains.contracts.services.FilmService;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class CatalogoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}

	@Autowired
	CategoryService srvCat;
	
	@Autowired
	ActorService srvActor;
	
	@Autowired
	FilmService srvFilm;
	
	@Autowired
	FilmRepository dao;
	@Autowired
	FilmService srv;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada...");
//		srvFilm.getAll().forEach(System.out::println);
//		var item = new Film(0, "Test", new Language(1), (byte)1, new BigDecimal(1.0), new BigDecimal(10.5));
//		item.addActor(new Actor(1));
//		item.addActor(new Actor(2));
//		item.addActor(new Actor(3));
//		item.addCategory(1);
//		item.addCategory(2);
//		dao.save(item);
//		System.err.println(item);
//		item = dao.getOne(dao.findAll().size()-1);
//		item.setTitle("KKKKKKK");
//		item.removeActor(new Actor(1));
//		item.addActor(new Actor(4));
//		item.removeCategory(new Category(2));
//		item.addCategory(3);
//		dao.save(item);
//		System.err.println(item);

	}

}
