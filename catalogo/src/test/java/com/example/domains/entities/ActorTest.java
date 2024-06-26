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

@DisplayName("Validaciones de actor")
class ActorTest {


	@DisplayName("Crear un actor")
	@Nested
	class Create {
		@Nested
		@DisplayName("Crear un actor con datos válidos")
		class OK {
			@ParameterizedTest(name = "{0} {1} {2}")
			@CsvSource(value = { "1", "2,Pepito,Grillo" })
			@DisplayName("Actor con nombre, apellidos e id válidos")
			void testCrearUnActorConDatosVálidos(ArgumentsAccessor args) {
				var actor = args.size() == 3 ? new Actor(args.getInteger(0), args.getString(1), args.getString(2))
						: new Actor(args.getInteger(0));
				assertNotNull(actor);
				assertAll("Actor", () -> assertEquals(args.getInteger(0), actor.getActorId(), "id"),
						() -> assertTrue(
								args.size() == 3 ? !actor.getFirstName().isBlank() : actor.getFirstName() == null,
								"nombre"),
						() -> assertTrue(
								args.size() == 3 ? !actor.getLastName().isBlank() : actor.getLastName() == null,
								"apellidos"));
			}
		}

		@Nested
		@DisplayName("Actor con datos inválidos")
		class KO {
			@ParameterizedTest(name = "id: -{0}-")
			@ValueSource(ints = {-1, 1234567890})
			@Positive
			@DisplayName("Actor con id negativa o demasiado larga")
			void testIdInválido(int arg) {
				 var actor = new Actor(arg);
				 assertTrue(actor.isInvalid());
			}
			
			@ParameterizedTest(name = "nombre: -{0}-")
			@ValueSource(strings = {" ", "      ", "x", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
			@NullAndEmptySource
			@DisplayName("Actor con nombre vacío, demasiado corto o demasiado largo")
			void testNombreInválido(String arg) {
				 var actor = new Actor(1, arg, "Grillo");
				 assertTrue(actor.isInvalid());
			}
			
			@ParameterizedTest(name = "apellido: -{0}-")
			@ValueSource(strings = {" ", "      ", "x", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
			@NullAndEmptySource
			@DisplayName("Actor con apellido vacío, demasiado corto o demasiado largo")
			void testApellidoInválido(String arg) {
				 var actor = new Actor(1, "Pepito", arg);
				 assertTrue(actor.isInvalid());
			}
		}
	}

	

	

}
