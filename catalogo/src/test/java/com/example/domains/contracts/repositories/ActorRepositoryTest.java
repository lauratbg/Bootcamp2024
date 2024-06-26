package com.example.domains.contracts.repositories;
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
@DisplayName("'ActorRepositoy' tests")
class ActorRepositoryTest {

	@Autowired
	ActorRepository dao;

	@Nested
	@DisplayName("Funcionamiento de métodos 'find'")
	class finds {
		@Nested
		@DisplayName("Tests válidos")
		class OK {
			@Test
			@DisplayName("Se listan todos los actores")
			void testFindAll() {
				assertThat(dao.findAll().size()).isGreaterThan(200);
			}
			
			@Test
			@DisplayName("El actor con id = 1 es 'PENELOPE'")
			void testFindById() {
				assertThat(dao.findById(1).get().getFirstName()).isEqualTo("PENELOPE");
			}
		}
		
		@Nested
		@DisplayName("Tests inválidos")
		class KO {
			@ParameterizedTest(name = "{0}")
			@CsvSource(value = { "220", "-1" })
			@DisplayName("Salta excepción cuando no existen actores con una id")
			void testFindById(int arg) {
				assertThrows(NoSuchElementException.class, () -> dao.findById(arg).get());
			}
		}
	}

}
