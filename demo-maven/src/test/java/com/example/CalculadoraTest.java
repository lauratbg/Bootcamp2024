package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.example.core.test.Smoke;

@DisplayName("Pruebas de la clase Calculadora")
class CalculadoraTest {
	Calculadora calculadora;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		calculadora = new Calculadora();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Nested
	@DisplayName("Metodo Add")
	class Add {
		@Nested
		class OK {
			@Test
			@DisplayName("Suma dos enteros")
			@RepeatedTest(value = 5, name = "{displayName} {currentRepetition}/{totalRepetitions}")
			void testAdd() {
//				var calculadora = new Calculadora();
				assertEquals(3, calculadora.add(1, 2));
//				assertEquals(3, calculadora.add(4, -1), "caso 4, -1");
//				assertEquals(3, calculadora.add(-1, -1), "caso -1, -1");
//				assertEquals(3, calculadora.add(0, 0), "caso 0,0");
			}

			/*
			 * Práctico para meter varios casos en uno. En el parameterized metes el caso y
			 * en el csvsoource las "listas" con los operandos y el resultado esperado
			 * 
			 */
			@ParameterizedTest(name = "Caso {index}: {0} + {1} = {2}")
			@DisplayName("Suma dos enteros")
			@CsvSource(value = { "1,2,3", "3,-1,2", "-1,2,1", "-1,-1,-2" })
			void testAdd(double op1, double op2, double res) {
				assertEquals(res, calculadora.add(op1, op2));
			}

			// Prueba de humo
			@Test
			@Smoke
			@DisplayName("Suma IEEE7..")
			void testAdd2() {
//				var calculadora = new Calculadora();

				var result = calculadora.add(0.1, 0.2);

				assertEquals(0.3, result);
			}

		}

		@Nested
		class KO {

		}
	}

	@Nested
	@DisplayName("Metodo Div")
	class Div {
		Calculadora calculadora;

		@BeforeEach
		void setUp() throws Exception {
			calculadora = new Calculadora();
		}

		@Nested
		class OK {
			@Test
			@DisplayName("Divide dos enteros")
			void testDivInt() {
//				var calculadora = new Calculadora();

				var result = calculadora.div(3, 2);

				assertEquals(1, result);
			}

			@Test
			@DisplayName("Divide dos reales")
			void testDivRealOK() {
//				var calculadora = new Calculadora();

				var result = calculadora.div(3.0, 2.0);

				assertEquals(1.5, result);
			}

		}

		@Nested
		class KO {
			@Test
			@DisplayName("Division por 0")
			void testDivRealKO() {
//				var calculadora = new Calculadora();

				assertThrows(ArithmeticException.class, () -> calculadora.div(3.0, 0));
			}

		}
		
		@Test
		void simula() {
			Calculadora calculadora = mock(Calculadora.class);
			//aunque 2+2 = 4, yo mockeo que es 3 y va a ser 3
//			when(calculadora.add(2, 2)).thenReturn(3.0);
			
			// le pase lo que le pase va a devolver siempre 3
//			when(calculadora.add(anyDouble(), anyDouble())).thenReturn(3.0);
			
			// primero va a devolver 3, y las siguientes veces 4
			when(calculadora.add(anyDouble(), anyDouble())).thenReturn(3.0).thenReturn(4.0);


			assertEquals(3, calculadora.add(2.0, 2.0));
			//solo suplanta el del valor que he metido
			assertEquals(4, calculadora.add(12.0, 2.0));

		}
	}

}
