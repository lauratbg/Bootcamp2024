package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.validation.constraints.Positive;

@DisplayName("Validaciones de category")
class CategoryTest {

	@DisplayName("Crear una categoría")
	@Nested
	class Create {
		@Nested
		@DisplayName("Crear un categoría con datos válidos")
		class OK {
			@ParameterizedTest(name = "{0} {1} {2}")
			@CsvSource(value = { "1", "2,Nueva" })
			@DisplayName("Categoría con nombre e id válidos")
			void testCrearUncategoríaConDatosVálidos(ArgumentsAccessor args) {
				var category = args.size() == 2 ? new Category(args.getInteger(0), args.getString(1) )
						: new Category(args.getInteger(0));
				assertNotNull(category);
				assertAll("category", () -> assertEquals(args.getInteger(0), category.getCategoryId(), "id"),
						() -> assertTrue(
								args.size() == 2 ? !category.getName().isBlank() : category.getName() == null,
								"nombre"));
			}
		}

		@Nested
		@DisplayName("categoría con datos inválidos")
		class KO {
			@ParameterizedTest(name = "id: -{0}-")
			@ValueSource(ints = { -1, 1234567890 })
			@Positive
			@DisplayName("category con id negativa o demasiado larga")
			void testIdInválido(int arg) {
				var category = new Category(arg);
				assertTrue(category.isInvalid());
			}

			@ParameterizedTest(name = "nombre: -{0}-")
			@ValueSource(strings = { " ", "      ", "x",
					"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" })
			@NullAndEmptySource
			@DisplayName("category con nombre vacío, demasiado corto o demasiado largo")
			void testNombreInválido(String arg) {
				var category = new Category(1, arg);
				assertTrue(category.isInvalid());
			}

		}
	}

}
