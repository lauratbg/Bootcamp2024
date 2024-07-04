package com.example.application.resources;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Film;
import com.example.domains.entities.models.ActorDTO;
import com.example.domains.entities.models.FilmDTO;
import com.example.domains.entities.models.FilmEditDTO;
import com.example.domains.entities.models.FilmShort;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/peliculas/v1")
@Tag(name = "peliculas-service", description = "Mantenimiento de películas")
public class FilmResource {
	private final FilmService srv;

	public FilmResource(FilmService srv) {
		this.srv = srv;
	}

	@Operation(summary = "Obtener todas las películas", description = "Obtiene una lista de todos los actores en formato largo o corto")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de películas obtenida correctamente"),
			@ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
			@ApiResponse(responseCode = "500", description = "Error en el servidor") })
	@GetMapping
	public List<?> getAll(
			@Parameter(description = "Modo de visualización: 'short' para vista corta, 'long' para vista detallada")
			@RequestParam(required = false, defaultValue = "long") String modo) {
		if (modo.equals("short")) {
			return srv.getAll().stream()
					.map(o -> new FilmShort(o.getFilmId(), o.getTitle()))
					.toList();
		}
		return srv.getAll().stream()
				.map(o -> new FilmDTO(o.getFilmId(), o.getTitle(), o.getLanguage(), o.getRentalDuration(), o.getRentalRate(), o.getReplacementCost()))
				.toList();
	}

	@Operation(summary = "Obtener películas paginados", description = "Obtiene una lista paginada de películas")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de películas paginada obtenida correctamente"),
			@ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
			@ApiResponse(responseCode = "500", description = "Error en el servidor") })
	@GetMapping(params = "page")
	public Page<Film> getAll(Pageable page) {
		return srv.getAll(page);
	}

	@Operation(summary = "Obtener una película", description = "Obtiene los detalles de una película específico por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Película obtenido correctamente"),
			@ApiResponse(responseCode = "404", description = "Película no encontrado") })	@GetMapping(path = "/{id}")
	public Film getOne(
			@Parameter(description = "ID de la película")
			@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if (item.isEmpty())
			throw new NotFoundException();
		return item.get();
	}

	record Rental(int filmId, @Schema(description = "Que el titulo contenga") String title, @Schema(description = "Precio de alquiler")BigDecimal rental) {}

	@Operation(summary = "Obtener precio de alquiler de una película por ID", description = "Devuelve el precio de alquiler de una película específica por su ID")
	@GetMapping(path = "/{id}/rental")
	public Rental getRental(
			@Parameter(description = "ID de la película para obtener el precio de su alquiler")
			@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if (item.isEmpty())
			throw new NotFoundException();
		return new Rental(item.get().getFilmId(), item.get().getTitle(), item.get().getRentalRate());
	}

	@Operation(summary = "Obtener actores de una película por ID", description = "Devuelve una lista de actores de una película específica por su ID")
	@GetMapping(path = "/{id}/actores")
	public List<ActorDTO> getActores(
			@Parameter(description = "ID de la película para obtener los actores")
			@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if (item.isEmpty())
			throw new NotFoundException();
		return item.get().getActors().stream()
				.map(ActorDTO::from)
				.collect(Collectors.toList());
	}

	@Operation(summary = "Obtener películas por idioma", description = "Devuelve una lista de películas por su idioma")
	@GetMapping(path = "/lang/{language}")
	public ResponseEntity<?> getByLanguage(
			@Parameter(description = "Nombre del idioma de las películas")
			@PathVariable String language) throws NotFoundException {
		List<FilmShort> films = new ArrayList<>();
		for (Film f : srv.getAll())
			if (f.getLanguage().getName().equalsIgnoreCase(language))
				films.add(FilmShort.from(f));
		if (films.isEmpty())
			return new ResponseEntity<>("Idioma no encontrado", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(films, HttpStatus.OK);
	}

	@Operation(summary = "Obtener películas por calificación", description = "Devuelve una lista de películas por su calificación")
	@GetMapping(path = "/rating/{rating}")
	public ResponseEntity<?> getByRating(
			@Parameter(
				    description = "La clasificación por edades asignada a la película", 
				    examples = {
				        @ExampleObject(value = "G"),
				        @ExampleObject(value = "PG"),
				        @ExampleObject(value = "PG-13"),
				        @ExampleObject(value = "R"),
				        @ExampleObject(value = "NC-17")
				    }
				) @PathVariable String rating) throws NotFoundException {
		List<FilmShort> films = new ArrayList<>();
		for (Film f : srv.getAll())
			if (f.getRating().getValue().equalsIgnoreCase(rating))
				films.add(FilmShort.from(f));
		if (films.isEmpty())
			return new ResponseEntity<>("Calificación no encontrada", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(films, HttpStatus.OK);
	}

	@Operation(summary = "Añadir una nueva película", description = "Agrega una nueva película a la base de datos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Película creado correctamente"),
			@ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
			@ApiResponse(responseCode = "409", description = "Conflicto: Clave duplicada"),
			@ApiResponse(responseCode = "422", description = "Datos inválidos") })
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@Transactional
	public ResponseEntity<Object> add(
			@Valid @RequestBody FilmEditDTO item) throws Exception {
		Film newItem = srv.add(FilmEditDTO.from(item));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newItem.getFilmId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@Operation(summary = "Editar una película existente", description = "Edita una película existente en la base de datos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Película actualizada correctamente"),
			@ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
			@ApiResponse(responseCode = "404", description = "Película no encontrada"),
			@ApiResponse(responseCode = "422", description = "Datos inválidos") })
	@PutMapping(path = "/{id}")
	public FilmEditDTO modify(
			@Parameter(description = "ID de la película que se quiere editar")
			@PathVariable int id,
			@Valid @RequestBody FilmEditDTO item) throws Exception {
		if (item.getFilmId() != id)
			throw new BadRequestException("No coinciden los ids");
		return FilmEditDTO.from(srv.modify(FilmEditDTO.from(item)));
	}

	@Operation(summary = "Eliminar una película", description = "Elimina una película de la base de datos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Película eliminada")})
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(
			@Parameter(description = "ID de la película a eliminar")
			@PathVariable int id) {
		srv.deleteById(id);
	}
}
