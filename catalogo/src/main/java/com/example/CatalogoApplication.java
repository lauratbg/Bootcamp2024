package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.contracts.services.CategoryService;

@SpringBootApplication
public class CatalogoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}

	@Autowired
	CategoryService srvCat;
	
	@Autowired
	ActorService srvActor;

	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada...");
		srvActor.getAll().forEach(System.out::println);
		
	}

}
