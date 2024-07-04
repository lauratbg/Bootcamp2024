package com.example;

import java.util.TreeMap;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.transaction.Transactional;

@SpringBootApplication
@OpenAPIDefinition(
		 info = @Info(title = "Microservicio: Catálogo", version = "1.0",
		 description = "Aprendiendo microservicios."
		 ),
		 externalDocs = @ExternalDocumentation(description = "Documentación del proyecto", url = "https://github.com/lauratbg/Bootcamp2024")
		)
public class CatalogoApplication implements CommandLineRunner {

	@Bean
	public OpenApiCustomizer sortSchemasAlphabetically() {
	 return openApi -> {
	 var schemas = openApi.getComponents().getSchemas();
	 openApi.getComponents().setSchemas(new TreeMap<>(schemas));
	 };
	}
	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}


	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada...");

	}

}
