package com.example.domains.contracts.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("'Service' test")
class ActorServiceTest {

	@Autowired
	ActorService srv;
	
	@Nested
	@DisplayName("Funcionamiento de métodos 'get'")
	class finds {
		@Nested
		@DisplayName("Tests válidos")
		class OK {
			@Test
			@DisplayName("Se listan todos los actores")
			void testGetAll() {
				assertThat(srv.getAll().size()).isGreaterThan(200);
			}
			
			@Test
			@DisplayName("El actor con id = 1 es 'PENELOPE'")
			void testGetOne() {
				assertThat(srv.getOne(1).get().getFirstName()).isEqualTo("PENELOPE");
			}
		}
		
		@Nested
		@DisplayName("Tests inválidos")
		class KO {
			@ParameterizedTest(name = "{0}")
			@CsvSource(value = { "220", "-1" })
			@DisplayName("Salta excepción cuando no existen actores con una id")
			void testFindById(int arg) {
				assertThrows(NoSuchElementException.class, () -> srv.getOne(arg).get());
			}
		}
	}

}
