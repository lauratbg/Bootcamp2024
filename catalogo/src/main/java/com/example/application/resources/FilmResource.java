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

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/peliculas/v1")
public class FilmResource {
	private FilmService srv;

	
	public FilmResource(FilmService srv) {
		this.srv = srv;
	}

	@GetMapping
	public List getAll(@RequestParam(required = false, defaultValue = "long") String modo) {
		if (modo.equals("short"))
			return srv.getAll().stream().map(o -> new FilmShort(o.getFilmId(), o.getTitle()))
					.toList();
		return srv.getAll().stream().map(o -> new FilmDTO(o.getFilmId(), o.getTitle(), o.getLanguage(), o.getRentalDuration(), o.getRentalRate(), o.getReplacementCost()))
					.toList();

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
	
	record Rental(int filmId, String title, BigDecimal rental) {	}
	
	@GetMapping(path = "/{id}/rental")
	public Rental getRental(@PathVariable int id) throws NotFoundException { 												
		var item = srv.getOne(id);
		if (item.isEmpty())
			throw new NotFoundException();
		return new Rental(item.get().getFilmId(), item.get().getTitle(), item.get().getRentalRate());

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
	
	@GetMapping(path = "/lang/{language}")
	public ResponseEntity<?> getByLanguage(@PathVariable String language) throws NotFoundException { 	
		List<FilmShort> films = new ArrayList<FilmShort>();
		for(Film f : srv.getAll())
			if(f.getLanguage().getName().toUpperCase().equals(language.toUpperCase()))
				films.add(FilmShort.from(f));
		if(films.isEmpty())
			  return new ResponseEntity<>("Language not found", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(films, HttpStatus.OK);
	}
	
	@GetMapping(path = "/rating/{rating}")
	public ResponseEntity<?> getByRating(@PathVariable String rating) throws NotFoundException { 	
		List<FilmShort> films = new ArrayList<FilmShort>();
		for(Film f : srv.getAll())
			if(f.getRating().getValue().toUpperCase().equals(rating.toUpperCase()))
				films.add(FilmShort.from(f));
		if(films.isEmpty())
			  return new ResponseEntity<>("Language not found", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(films, HttpStatus.OK);
	}


	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@Transactional
	public ResponseEntity<Object> add(@RequestBody FilmEditDTO item) throws Exception {
		Film newItem = srv.add(FilmEditDTO.from(item));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newItem.getFilmId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "/{id}")
	public FilmEditDTO modify(
		@PathVariable int id,
			@Valid @RequestBody FilmEditDTO item) throws Exception {
		if (item.getFilmId() != id)
			throw new BadRequestException("No coinciden los identificadores");
		return FilmEditDTO.from(srv.modify(FilmEditDTO.from(item)));
	}


	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT) // 204
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}

}