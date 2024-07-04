package com.example.application.resources;

import java.net.URI;
import java.util.List;

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

import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.models.ActorDTO;
import com.example.domains.entities.models.ActorShort;
import com.example.domains.entities.models.FilmShort;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/actores/v1")
@Tag(name = "actores-service", description = "Mantenimiento de actores")
public class ActorResource {
	private ActorService srv;

	public ActorResource(ActorService srv) {
		this.srv = srv;
	}

	@Operation(summary = "Obtener todos los actores", description = "Obtiene una lista de todos los actores en formato largo o corto")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de actores obtenida correctamente"),
			@ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
			@ApiResponse(responseCode = "500", description = "Error en el servidor") })
	@GetMapping
	public List<?> getAll(
			@Parameter(description = "Modo de visualización: 'long' o 'short'", example = "short") @RequestParam(required = false, defaultValue = "long") String modo) {
		if (modo.equals("short"))
			return srv.getByProjection(ActorShort.class);
		return srv.getByProjection(ActorDTO.class);
	}

	@Operation(summary = "Obtener actores paginados", description = "Obtiene una lista paginada de actores en formato corto")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de actores paginada obtenida correctamente"),
			@ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
			@ApiResponse(responseCode = "500", description = "Error en el servidor") })
	@GetMapping(params = "page")
	public Page<ActorShort> getAll(Pageable page) {
		return srv.getByProjection(page, ActorShort.class);
	}

	@Operation(summary = "Obtener un actor", description = "Obtiene los detalles de un actor específico por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Actor obtenido correctamente"),
			@ApiResponse(responseCode = "404", description = "Actor no encontrado") })
	@GetMapping(path = "/{id}")
	public ActorDTO getOne(@Parameter(description = "ID del actor", example = "1") @PathVariable int id)
			throws NotFoundException {
		var item = srv.getOne(id);
		if (item.isEmpty())
			throw new NotFoundException();
		return ActorDTO.from(item.get());
	}

	@Operation(summary = "Obtener películas de un actor", description = "Obtiene una lista de películas en las que ha participado un actor específico por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de películas obtenida correctamente"),
			@ApiResponse(responseCode = "404", description = "Actor no encontrado") })
	@GetMapping(path = "/{id}/pelis")
	@Transactional
	public List<FilmShort> getPelis(@Parameter(description = "ID del actor", example = "1") @PathVariable int id)
			throws NotFoundException {
		var item = srv.getOne(id);
		if (item.isEmpty())
			throw new NotFoundException();
		return item.get().getFilmActors().stream()
				.map(o -> new FilmShort(o.getFilm().getFilmId(), o.getFilm().getTitle())).toList();
	}

	@Operation(summary = "Jubilar un actor", description = "Marca a un actor como jubilado por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Actor marcado como jubilado correctamente"),
			@ApiResponse(responseCode = "404", description = "Actor no encontrado") })
	@DeleteMapping(path = "/{id}/jubilacion")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void jubilar(@Parameter(description = "ID del actor", example = "1") @PathVariable int id)
			throws NotFoundException {
		var item = srv.getOne(id);
		if (item.isEmpty())
			throw new NotFoundException();
		item.get().jubilate();
	}

	@Operation(summary = "Crear un nuevo actor", description = "Crea un nuevo actor con los detalles proporcionados")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Actor creado correctamente"),
			@ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
			@ApiResponse(responseCode = "409", description = "Conflicto: Clave duplicada"),
			@ApiResponse(responseCode = "422", description = "Datos inválidos") })
	@PostMapping
	public ResponseEntity<Object> create(@Parameter(description="Actor")@Valid @RequestBody ActorDTO item)
			throws BadRequestException, DuplicateKeyException, InvalidDataException {
		var newItem = srv.add(ActorDTO.from(item));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newItem.getActorId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@Operation(summary = "Actualizar un actor", description = "Actualiza los detalles de un actor específico por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Actor actualizado correctamente"),
			@ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
			@ApiResponse(responseCode = "404", description = "Actor no encontrado"),
			@ApiResponse(responseCode = "422", description = "Datos inválidos") })
	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@Parameter(description = "ID del actor", example = "1") @PathVariable int id,
			@Parameter(description="Actor") @Valid @RequestBody ActorDTO item) throws BadRequestException, NotFoundException, InvalidDataException {
		if (id != item.getActorId())
			throw new BadRequestException("No coinciden los ids");
		srv.modify(ActorDTO.from(item));
	}

	@Operation(summary = "Eliminar un actor", description = "Elimina un actor específico por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Actor eliminado correctamente"),
			@ApiResponse(responseCode = "404", description = "Actor no encontrado") })
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@Parameter(description = "ID del actor", example = "1") @PathVariable int id) {
		srv.deleteById(id);
	}
}
