package com.example.application.resources;



import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Language;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(LanguageResource.class)
class LanguageResourceTest {
	@Autowired
    private MockMvc mockMvc;

	@MockBean
	private LanguageService srv;

	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}


	@Test
	void testGetAllString() throws Exception {
		List<Language> lista = new ArrayList<>(
		        Arrays.asList(new Language(1, "Inglés"),
		        		new Language(2, "Español"),
		        		new Language(3, "Chino")));
		when(srv.getAll()).thenReturn(lista);
		mockMvc.perform(get("/api/languages/v1").accept(MediaType.APPLICATION_JSON))
			.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.size()").value(3)
					);
	}


	@Test
	void testGetOne() throws Exception {
		int id = 1;
		var ele = new Language(id, "Inglés");
		when(srv.getOne(id)).thenReturn(Optional.of(ele));
		mockMvc.perform(get("/api/languages/v1/{id}", id))
			.andExpect(status().isOk())
	        .andExpect(jsonPath("$.id").value(id))
	        .andExpect(jsonPath("$.name").value(ele.getName()))
	        .andDo(print());
	}
	
	@Test
	void testGetOne404() throws Exception {
		 when(srv.getOne(anyInt())).thenReturn(Optional.empty());

	        mockMvc.perform(get("/{id}", 1))
	                .andExpect(status().isNotFound());
	}

	@Test
	@Disabled
	void testCreate() throws Exception {
		int id = 1;
		var ele = new Language(id, "Inglés");
		when(srv.add(ele)).thenReturn(ele);
		mockMvc.perform(post("/api/languages/v1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(ele))
			)
			.andExpect(status().isCreated())
	        .andExpect(header().string("Location", "http://localhost/api/languages/v1/1"))
	        .andDo(print())
	        ;
	}


}
