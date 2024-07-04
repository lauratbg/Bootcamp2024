package com.example;

import java.util.TreeMap;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.transaction.Transactional;

@SpringBootApplication
@EnableDiscoveryClient
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
	@Bean 
	@Primary
	public RestTemplate restTemplate(RestTemplateBuilder builder) { // sin balancear
		return builder.build();
	}

	@Bean 
	@LoadBalanced
	public RestTemplate restTemplateLB(RestTemplateBuilder builder) {
		return builder.build();
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
