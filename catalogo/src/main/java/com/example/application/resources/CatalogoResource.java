package com.example.application.resources;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.contracts.CatalogoService;
import com.example.application.models.NovedadesDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/catalogo/v1")
@Tag(name = "catalogo-service", description = "Mantenimiento del catálogo")

public class CatalogoResource {
	@Autowired
	private CatalogoService srv;

	public CatalogoResource(CatalogoService srv) {
		this.srv = srv;
	}
	
	@Operation(summary = "Obtener las novedades", description = "Devuelve las últimas novedades")
	@GetMapping("/novedades")
	public NovedadesDTO getNovedades(@RequestParam(required = false) String fecha) {
		Timestamp timestamp = null;
		if (fecha != null) {
			timestamp = Timestamp.valueOf(fecha);
		}
		return srv.novedades(timestamp);
	}
}
