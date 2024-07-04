package com.example.application.resources;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.CategoryService;
import com.example.domains.entities.Category;
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
@RequestMapping("/api/categorias/v1")
@Tag(name = "categorias-service", description = "Mantenimiento de categorias")

public class CategoryResource {
	private CategoryService srv;

	public CategoryResource(CategoryService srv) {
		this.srv = srv;
	}

	@Operation(summary = "Obtener todos las categorías", description = "Obtiene una lista de todas las categorías")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de categorías obtenida correctamente"),
			@ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
			@ApiResponse(responseCode = "500", description = "Error en el servidor") })
	@GetMapping
	public List<Category> getAll() {
		return srv.getAll();

	}


	@Operation(summary = "Obtener una categoría", description = "Obtiene los detalles de una categoría específico por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Categoría obtenido correctamente"),
			@ApiResponse(responseCode = "404", description = "Categoría no encontrado") })
	@GetMapping(path = "/{id}")
	public Category getOne(@Parameter(description = "ID de la categoría", example = "1") @PathVariable int id) throws NotFoundException { 
		var item = srv.getOne(id);
		if (item.isEmpty())
			throw new NotFoundException();
		return item.get();

	}

	@Operation(summary = "Obtener películas", description = "Obtiene las películas según la categoría")
	@GetMapping(path = "/{id}/pelis")
	@Transactional
	public List<FilmShort> getPelis(@Parameter(description="ID de la categoría", example = "1")@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if (item.isEmpty())
			throw new NotFoundException();
		return item.get().getFilmCategories().stream().map(o -> new FilmShort(o.getFilm().getFilmId(), o.getFilm().getTitle()))
				.toList();
	}

	@Operation(summary = "Crear una categoría", description = "Crea una nueva categoría")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Categoría creada"),
			@ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
			@ApiResponse(responseCode = "409", description = "Conflicto: Clave duplicada"),
			@ApiResponse(responseCode = "422", description = "Datos inválidos") })
	
	@PostMapping
	public ResponseEntity<Object> create(@Parameter(description="Categoría a crear")@Valid @RequestBody Category item)
			throws BadRequestException, DuplicateKeyException, InvalidDataException {
		var newItem = srv.add(item);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newItem.getCategoryId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@Operation(summary = "Actualizar una categoría", description = "Actualiza los detalles de una categoría específico por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Categoría actualizado correctamente"),
			@ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
			@ApiResponse(responseCode = "404", description = "Categoría no encontrado"),
			@ApiResponse(responseCode = "422", description = "Datos inválidos") })
	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT) // 204
	public void update(@Parameter(description="ID de la categoría", example = "1")@PathVariable int id, @Parameter(description="Nuevos datos de la categoría a actualizar")@Valid @RequestBody Category item)
			throws BadRequestException, NotFoundException, InvalidDataException {
		if (id != item.getCategoryId())
			throw new BadRequestException("No coinciden los ids");
		srv.modify(item);
	}

	@Operation(summary = "Eliminar una categoría", description = "Elimina una categoría específica por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Categoría eliminada"),
			@ApiResponse(responseCode = "404", description = "Categoría no encontrada") })
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT) // 204
	public void delete(@Parameter(description="ID de la categoría", example = "1")@PathVariable int id) {
		srv.deleteById(id);
	}

}
