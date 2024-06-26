package com.example.domains.contracts.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domains.entities.Actor;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

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
			@DisplayName("El actor con id = 1 es 'Pepito'")
			void testGetOne() {
				assertThat(srv.getOne(1).get().getFirstName()).isEqualTo("Pepito");
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

	@Nested
	@DisplayName("Funcionamiento de métodos CRUD")
	class crud {
		@Nested
		@DisplayName("Tests válidos")
		class OK {
			@Test
			@DisplayName("Añadir")
			void testAdd() {
				try {
					srv.add(new Actor(211, "Pepito", "Grillo"));
				} catch (DuplicateKeyException e) {
					e.printStackTrace();
				} catch (InvalidDataException e) {
					e.printStackTrace();
				}
				assertThat(srv.getOne(211).get().getFirstName()).isEqualTo("Pepito");

			}

			@Test
			@DisplayName("Modificar")
			void testModify() {
				try {
					srv.modify(new Actor(123, "Pepito", "Grillo"));
				} catch (NotFoundException e) {
					e.printStackTrace();
				} catch (InvalidDataException e) {
					e.printStackTrace();
				}
				assertThat(srv.getOne(123).get().getFirstName()).isEqualTo("Pepito");

			}

			@Test
			@DisplayName("Borrar por id")
			void testDeleteById() {
				srv.deleteById(210);
				assertThrows(NoSuchElementException.class, () -> srv.getOne(210).get());
			}
			
			@Test
			@DisplayName("Borrar actor")
			void testDelete() {
				try {
					srv.delete(new Actor(210));
				} catch (InvalidDataException e) {
					e.printStackTrace();
				}
				assertThrows(NoSuchElementException.class, () -> srv.getOne(210).get());
			}
		}

		@Nested
		@DisplayName("Tests inválidos")
		class KO {
			@Test
			@DisplayName("Añadir actor con id duplicada")
			void testAddIdDuplicada() {
				assertThrows(DuplicateKeyException.class, () -> srv.add(new Actor(1, "PENELOPE", "Grillo")));
			}

			@ParameterizedTest(name = "{0} {1} {2}")
			@CsvSource(value = { "211, ,Grillo", "212,        ,Grillo", "211,x,Grillo",
					"211,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,Grillo" })
			@DisplayName("Añadir actor con nombre inválido")
			void testAddNombreInvalido(ArgumentsAccessor args) {
				assertThrows(InvalidDataException.class,
						() -> srv.add(new Actor(args.getInteger(0), args.getString(1), args.getString(2))));
			}

			@ParameterizedTest(name = "{0} {1} {2}")
			@CsvSource(value = { "211,Pepito, ", "212,Pepito,      ", "211,Pepito,x",
					"211,Pepito,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" })
			@DisplayName("Añadir actor con apellido inválido")
			void testAddApellidoInvalido(ArgumentsAccessor args) {
				assertThrows(InvalidDataException.class,
						() -> srv.add(new Actor(args.getInteger(0), args.getString(1), args.getString(2))));
			}

			@Test
			@DisplayName("Modificar actor con id inexistente")
			void testModificarIdNoExiste() {
				assertThrows(NotFoundException.class, () -> srv.modify(new Actor(215, "Pepito", "Grillo")));
			}

			@ParameterizedTest(name = "{0} {1} {2}")
			@CsvSource(value = { "211, ,Grillo", "212,        ,Grillo", "211,x,Grillo",
					"211,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,Grillo" })
			@DisplayName("Modificar actor con nombre inválido")
			void testModifyNombreInvalido(ArgumentsAccessor args) {
				assertThrows(InvalidDataException.class,
						() -> srv.modify(new Actor(args.getInteger(0), args.getString(1), args.getString(2))));
			}

			@ParameterizedTest(name = "{0} {1} {2}")
			@CsvSource(value = { "211,Pepito, ", "212,Pepito,      ", "211,Pepito,x",
					"211,Pepito,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" })
			@DisplayName("Modificar actor con apellido inválido")
			void testModificarApellidoInvalido(ArgumentsAccessor args) {
				assertThrows(InvalidDataException.class,
						() -> srv.modify(new Actor(args.getInteger(0), args.getString(1), args.getString(2))));
			}
			
			@Test
			@DisplayName("Borrar actor nulo")
			void testBorrarActorNull() {
				assertThrows(InvalidDataException.class, () -> srv.delete(null));
			}
		}
	}

}
