package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.validation.constraints.Positive;

@DisplayName("Validaciones de película")
class FilmTest {

	@DisplayName("Crear una película")
	@Nested
	class Create {
		@Nested
		@DisplayName("Película con datos válidos")
		class OK {
			@ParameterizedTest(name = "{0} {1} {2}")
			@CsvSource(value = { "1", "2,2,Lo imposible" })
			@DisplayName("Película con datos válidos")
			void testCrearUnaPelículaConDatosVálidos(ArgumentsAccessor args) {
				var film = args.size() == 3 ? new Film(args.getInteger(0), args.getString(1), args.getString(2))
						: new Film(args.getInteger(0));
				assertNotNull(film);
				assertAll("Película", () -> assertEquals(args.getInteger(0), film.getFilmId(), "id"),
						() -> assertTrue(
								args.size() == 3 ? !film.getTitle().isBlank() : film.getTitle() == null,
								"title"),
						() -> assertTrue(
								args.size() == 3 ? film.getRating().length()==1 : film.getRating() == null,
								"rating"));
			}
		}

		@Nested
		@DisplayName("Película con datos inválidos")
		class KO {
			@ParameterizedTest(name = "id: -{0}-")
			@ValueSource(ints = {-1, 1234567890})
			@Positive
			@DisplayName("Película con id negativa o demasiado larga")
			void testIdInválido(int arg) {
				 var peli = new Actor(arg);
				 assertTrue(peli.isInvalid());
			}
			
			@ParameterizedTest(name = "title: -{0}-")
			@ValueSource(strings = {" ", "      ", "x", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
			@NullAndEmptySource
			@DisplayName("Título con nombre vacío, demasiado corto o demasiado largo")
			void testTituoloInválido(String arg) {
				 var film = new Film(1, "1", arg);
				 assertTrue(film.isInvalid());
			}
			
			@ParameterizedTest(name = "length: -{0}-")
			@ValueSource(strings = { "      ", "x", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
			@NullAndEmptySource
			@DisplayName("Rating inválido")
			void testRatingInválido(String arg) {
				 var film = new Actor(1, arg, "Se7en");
				 assertTrue(film.isInvalid());
			}
		}
	}
	
	@Test
	@DisplayName("Solo compara la PK")
	void testPrimaryKeyOK() {
		var fixture1 = new Film(666, "1", "La casa de papel");
		var fixture2 = new Film(666, "2", "Vis a vis");
		assertAll("PK", 
				() -> assertTrue(fixture1.equals(fixture2)),
				() -> assertTrue(fixture2.equals(fixture1)),
				() -> assertTrue(fixture1.hashCode() == fixture2.hashCode())
				);
	}

	@Test
	@DisplayName("Solo la PK diferente")
	void testPrimaryKeyKO() {
		var fixture1 = new Film(666, "1", "La casa de papel");
		var fixture2 = new Film(665, "2", "Vis a vis");
		assertAll("PK", 
				() -> assertFalse(fixture1.equals(fixture2)),
				() -> assertFalse(fixture2.equals(fixture1)),
				() -> assertTrue(fixture1.hashCode() != fixture2.hashCode())
				);
	}

	

	

}
