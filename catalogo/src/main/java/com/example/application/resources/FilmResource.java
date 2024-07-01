package com.example.application.resources;

import java.net.URI;
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
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/peliculas/v1")
public class FilmResource {
	private FilmService srv;

	record Peli(int id, String titulo) {}
	
	public FilmResource(FilmService srv) {
		this.srv = srv;
	}

	@GetMapping
	public List getAll(@RequestParam(required = false, defaultValue = "long") String modo) {
		if (modo.equals("short"))
			return srv.getAll().stream().map(o -> new Peli(o.getFilmId(), o.getTitle()))
					.toList();
		return srv.getAll();

	}
	
	@GetMapping(params = "page")
	public Page<Film> getAll(Pageable page) {
		return srv.getAll(page);
	}

	@GetMapping(path = "/{id}")
	public Film getOne(@PathVariable int id) throws NotFoundException { 												
		var item = srv.getOne(id);
		if (item.isEmpty())
			throw new NotFoundException();
		return item.get();

	}

	@GetMapping(path = "/{id}/actores")
	public List<ActorDTO> getActores(@PathVariable int id) throws NotFoundException { 												
		var item = srv.getOne(id);
		if (item.isEmpty())
			throw new NotFoundException();

		 return item.get().getActors().stream()
		            .map(ActorDTO::from)
		            .collect(Collectors.toList());
	}
	

	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody Film item)
			throws DuplicateKeyException, InvalidDataException {
		var newItem = srv.add(item);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newItem.getFilmId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT) // 204
	public void update(@PathVariable int id, @Valid @RequestBody Film item)
			throws BadRequestException, NotFoundException, InvalidDataException {
		if (id != item.getFilmId())
			throw new BadRequestException("No coinciden los ids");
		srv.modify(item);
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT) // 204
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}

}
