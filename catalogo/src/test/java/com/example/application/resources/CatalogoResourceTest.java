package com.example.application.resources;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.application.contracts.CatalogoService;
import com.example.application.models.NovedadesDTO;
import com.example.domains.entities.Category;
import com.example.domains.entities.Language;
import com.example.domains.entities.models.ActorDTO;
import com.example.domains.entities.models.ActorShort;
import com.example.domains.entities.models.FilmShort;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;

@WebMvcTest(CatalogoResource.class)
public class CatalogoResourceTest {
	@Autowired
    private MockMvc mockMvc;

	@MockBean
	private CatalogoService srv;

	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Value
	static class ActorShortMock implements ActorShort {
		int id;
		String nombre;
		@Override
		public int getActorId() {
			return id;
		}
		@Override
		public String getNombre() {
			
			return nombre;
		}
		
	}

	@Test
	void testGetNovedades() throws Exception {
		 NovedadesDTO novedades = new NovedadesDTO(
	                Arrays.asList(new FilmShort(1, "Film 1"), new FilmShort(2, "Film 2")),
	                Arrays.asList(new ActorDTO(1, "Actor 1", "Last 1"), new ActorDTO(2, "Actor 2", "Last 2")),
	                Arrays.asList(new Category(1, "Category 1"), new Category(2, "Category 2")),
	                Arrays.asList(new Language(1, "English"), new Language(2, "Spanish"))
	        );
		 when(srv.novedades(null)).thenReturn(novedades);
		mockMvc.perform(get("/api/catalogo/v1/novedades").accept(MediaType.APPLICATION_JSON))
			.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.size()").value(4)
					);
	}


}
