package com.example.domains.contracts.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@SpringBootTest
@DisplayName("'FilmService' test")
class FilmServiceTest {

    @Autowired
    FilmService srv;

    @Nested
    @DisplayName("Funcionamiento de métodos 'get'")
    class finds {
        @Nested
        @DisplayName("Tests válidos")
        class OK {
            @Test
            @DisplayName("Se listan todas las películas")
            void testGetAll() {
                assertThat(srv.getAll().size()).isGreaterThan(10);
            }

            @Test
            @DisplayName("La película con id = 1 existe")
            void testGetOne() {
                assertThat(srv.getOne(1).isPresent()).isTrue();
            }
        }

        @Nested
        @DisplayName("Tests inválidos")
        class KO {
            @ParameterizedTest(name = "{0}")
            @CsvSource(value = { "2000", "-1" })
            @DisplayName("Salta excepción cuando no existen películas con una id")
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
                Film film = new Film();
                film.setTitle("New Film");
                film.setFilmId(0); // Assuming 0 means a new film without an ID
                try {
                    srv.add(film);
                } catch (DuplicateKeyException | InvalidDataException e) {
                    e.printStackTrace();
                }
                assertEquals(sizeBefore + 1, srv.getAll().size());
            }

            @Test
            @DisplayName("Modificar")
            void testModify() {
                Optional<Film> filmOpt = srv.getOne(1);
                if (filmOpt.isPresent()) {
                    Film film = filmOpt.get();
                    film.setTitle("Updated Title");
                    try {
                        srv.modify(film);
                    } catch (NotFoundException | InvalidDataException e) {
                        e.printStackTrace();
                    }
                    assertThat(srv.getOne(1).get().getTitle()).isEqualTo("Updated Title");
                }
            }

            @Test
            @DisplayName("Borrar por id")
            @Disabled
            void testDeleteById() {
                srv.deleteById(1);
                assertThrows(NoSuchElementException.class, () -> srv.getOne(1).get());
            }
            
            @Test
            @DisplayName("Borrar película")
            @Disabled
            void testDelete() {
                Film film = new Film();
                film.setFilmId(2);
                try {
                    srv.delete(film);
                } catch (InvalidDataException e) {
                    e.printStackTrace();
                }
                assertThrows(NoSuchElementException.class, () -> srv.getOne(2).get());
            }
        }

        @Nested
        @DisplayName("Tests inválidos")
        class KO {
            @Test
            @DisplayName("Añadir película con id duplicada")
            void testAddIdDuplicada() {
                Film film = new Film();
                film.setFilmId(1); 
                film.setLanguage(new Language(20, "Bable"));
                film.setTitle("Lo imposible");
                film.setLength(1);
                assertThrows(DuplicateKeyException.class, () -> srv.add(film));
            }

            @ParameterizedTest(name = "{0} {1}")
            @CsvSource(value = { "211, ", "212,        ", "211,x", 
                    "211,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" })
            @DisplayName("Añadir película con título inválido")
            void testAddTituloInvalido(ArgumentsAccessor args) {
                Film film = new Film();
                film.setFilmId(args.getInteger(0));
                film.setTitle(args.getString(1));
                assertThrows(InvalidDataException.class, () -> srv.add(film));
            }

            @Test
            @DisplayName("Modificar película con id inexistente")
            void testModificarIdNoExiste() {
                Film film = new Film();
                film.setFilmId(3000); 
                film.setTitle("Nonexistent Film");
                film.setLanguage(new Language(20, "Bable"));
                film.setLength(1);
                assertThrows(NotFoundException.class, () -> srv.modify(film));
            }

            @ParameterizedTest(name = "{0} {1}")
            @CsvSource(value = { "211, ", "212,        ", "211,x", 
                    "211,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" })
            @DisplayName("Modificar película con título inválido")
            void testModifyTituloInvalido(ArgumentsAccessor args) {
                Film film = new Film();
                film.setFilmId(args.getInteger(0));
                film.setTitle(args.getString(1));
                assertThrows(InvalidDataException.class, () -> srv.modify(film));
            }

            @Test
            @DisplayName("Borrar película nula")
            void testBorrarPeliculaNull() {
                assertThrows(InvalidDataException.class, () -> srv.delete(null));
            }
        }
    }
}
