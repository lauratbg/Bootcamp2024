package com.example;

import java.util.TreeMap;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
		 info = @Info(title = "Microservicio: Demos", version = "1.0",
		 description = "**Demos** de Microservicios.",
		 license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.html"),
		 contact = @Contact(name = "Javier Martín", url = "https://github.com/jmagit", email = "support@example.com")
		 ),
		 externalDocs = @ExternalDocumentation(description = "Documentación del proyecto", url = "https://github.com/jmagit/curso")
		)
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// para ordenar los modelos de esquema
	@Bean
	public OpenApiCustomizer sortSchemasAlphabetically() {
		return openApi -> {
			var schemas = openApi.getComponents().getSchemas();
			openApi.getComponents().setSchemas(new TreeMap<>(schemas));
		};
	}

	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada...");
	}

}