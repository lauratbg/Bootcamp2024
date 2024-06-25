package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.models.ActorDTO;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	ActorService srv;

	
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada...");
		srv.getByProjection(ActorDTO.class).forEach(System.out::println);
	}


}
