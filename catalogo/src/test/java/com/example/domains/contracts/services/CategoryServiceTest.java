package com.example.domains.contracts.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.example.domains.entities.Category;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@SpringBootTest
@DisplayName("'CategoryService' test")
class CategoryServiceTest {

	@Autowired
	CategoryService srv;

	@Nested
	@DisplayName("Funcionamiento de métodos 'get'")
	class finds {
		@Nested
		@DisplayName("Tests válidos")
		class OK {
			@Test
			@DisplayName("Se listan todos las categorías")
			void testGetAll() {
				assertThat(srv.getAll().size()).isGreaterThan(15);
			}

			@Test
			@DisplayName("La categoría con id = 1 es 'Action'")
			void testGetOne() {
				assertThat(srv.getOne(1).get().getName()).isEqualTo("Action");
			}
		}

		@Nested
		@DisplayName("Tests inválidos")
		class KO {
			@ParameterizedTest(name = "{0}")
			@CsvSource(value = { "220", "-1" })
			@DisplayName("Salta excepción cuando no existen categorías con una id")
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
				int sizeBefore = srv.getAll().size() - 1;
				int id = srv.getOne(srv.getAll().size()).get().getCategoryId();
				try {
					srv.add(new Category(id + 1));


				} catch (DuplicateKeyException e) {
					e.printStackTrace();
				} catch (InvalidDataException e) {
					e.printStackTrace();
				}
				
				assertEquals(sizeBefore + 1, srv.getAll().size());


			}

			@Test
			@DisplayName("Modificar")
			void testModify() {
				try {
					srv.modify(new Category(12, "NoLlorar"));
				} catch (NotFoundException e) {
					e.printStackTrace();
				} catch (InvalidDataException e) {
					e.printStackTrace();
				}
				assertThat(srv.getOne(12).get().getName()).isEqualTo("NoLlorar");


			}

			@Test
			@DisplayName("Borrar por id")
			void testDeleteById() {
				srv.deleteById(17);
				assertThrows(NoSuchElementException.class, () -> srv.getOne(210).get());
			}
			
			@Test
			@DisplayName("Borrar categoría")
			void testDelete() {
				try {
					srv.delete(new Category(18));
				} catch (InvalidDataException e) {
					e.printStackTrace();
				}
				assertThrows(NoSuchElementException.class, () -> srv.getOne(18).get());
			}
		}

		@Nested
		@DisplayName("Tests inválidos")
		class KO {
			@Test
			@DisplayName("Añadir categoría con id duplicada")
			void testAddIdDuplicada() {
				assertThrows(DuplicateKeyException.class, () -> srv.add(new Category(1, "PENELOPE")));
			}

			@ParameterizedTest(name = "{0} {1}")
			@CsvSource(value = { "211, ", "212,        ", "211,x",
					"211,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" })
			@DisplayName("Añadir categoría con nombre inválido")
			void testAddNombreInvalido(ArgumentsAccessor args) {
				assertThrows(InvalidDataException.class,
						() -> srv.add(new Category(args.getInteger(0), args.getString(1))));
			}


			@Test
			@DisplayName("Modificar categoría con id inexistente")
			void testModificarIdNoExiste() {
				assertThrows(NotFoundException.class, () -> srv.modify(new Category(215, "Pepito")));
			}
			@ParameterizedTest(name = "{0} {1}")
			@CsvSource(value = { "211, ", "212,        ", "211,x",
					"211,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" })
			@DisplayName("Modificar categoría con nombre inválido")
			void testModifyNombreInvalido(ArgumentsAccessor args) {
				assertThrows(InvalidDataException.class,
						() -> srv.modify(new Category(args.getInteger(0), args.getString(1))));
			}

			@Test
			@DisplayName("Borrar categoría nula")
			void testBorrarActorNull() {
				assertThrows(InvalidDataException.class, () -> srv.delete(null));
			}
		}
	}

}
