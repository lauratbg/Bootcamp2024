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

import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Language;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/languages/v1")
public class LanguageResource {
	
	private LanguageService srv;

	public LanguageResource(LanguageService srv) {
		this.srv = srv;
	}

	@GetMapping
	public List<Language> getAll() {
		return srv.getAll();

	}


	@GetMapping(path = "/{id}")
	public Language getOne(@PathVariable int id) throws NotFoundException { 
		var item = srv.getOne(id);
		if (item.isEmpty())
			throw new NotFoundException();
		return item.get();

	}


	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Object> create(@Valid @RequestBody Language item)
			throws Exception {
		if (item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		srv.add(item);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(item.getLanguageId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT) // 204
	public void update(@PathVariable int id, @Valid @RequestBody Language item)
			throws BadRequestException, NotFoundException, InvalidDataException {
		if (id != item.getLanguageId())
			throw new BadRequestException("No coinciden los ids");
		srv.modify(item);
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT) // 204
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}

}
