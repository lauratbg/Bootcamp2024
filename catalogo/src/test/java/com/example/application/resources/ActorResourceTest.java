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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.models.ActorDTO;
import com.example.domains.entities.models.ActorShort;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;

@WebMvcTest(ActorResource.class)
class ActorResourceTest {
	@Autowired
    private MockMvc mockMvc;

	@MockBean
	private ActorService srv;

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
	void testGetAllString() throws Exception {
		List<ActorShort> lista = new ArrayList<>(
		        Arrays.asList(new ActorShortMock(1, "Pepito Grillo"),
		        		new ActorShortMock(2, "Carmelo Coton"),
		        		new ActorShortMock(3, "Capitan Tan")));
		when(srv.getByProjection(ActorShort.class)).thenReturn(lista);
		mockMvc.perform(get("/api/actores/v1?modo=short").accept(MediaType.APPLICATION_JSON))
			.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.size()").value(3)
					);
//		mvc.perform(get("/api/actores/v1").accept(MediaType.APPLICATION_XML))
//			.andExpectAll(
//					status().isOk(), 
//					content().contentType("application/json"),
//					jsonPath("$.size()").value(3)
//					);
	}

	@Test
	void testGetAllPageable() throws Exception {
		List<ActorShort> lista = new ArrayList<>(
		        Arrays.asList(new ActorShortMock(1, "Pepito Grillo"),
		        		new ActorShortMock(2, "Carmelo Coton"),
		        		new ActorShortMock(3, "Capitan Tan")));

		when(srv.getByProjection(PageRequest.of(0, 20), ActorShort.class))
			.thenReturn(new PageImpl<>(lista));
		mockMvc.perform(get("/api/actores/v1").queryParam("page", "0"))
			.andExpectAll(
				status().isOk(), 
				content().contentType("application/json"),
				jsonPath("$.content.size()").value(3),
				jsonPath("$.size").value(3)
				);
	}

	@Test
	void testGetOne() throws Exception {
		int id = 1;
		var ele = new Actor(id, "Pepito", "Grillo");
		when(srv.getOne(id)).thenReturn(Optional.of(ele));
		mockMvc.perform(get("/api/actores/v1/{id}", id))
			.andExpect(status().isOk())
	        .andExpect(jsonPath("$.id").value(id))
	        .andExpect(jsonPath("$.nombre").value(ele.getFirstName()))
	        .andExpect(jsonPath("$.apellidos").value(ele.getLastName()))
	        .andDo(print());
	}
	@Test
	void testGetOne404() throws Exception {
		 when(srv.getOne(anyInt())).thenReturn(Optional.empty());

	        // Realizar la solicitud y verificar que se lanza NotFoundException
	        mockMvc.perform(get("/{id}", 1))
	                .andExpect(status().isNotFound());
	}
//
//	@Test
//	void testGetPelis() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetPeliculas() {
//		fail("Not yet implemented");
//	}

	@Test
	void testCreate() throws Exception {
		int id = 1;
		var ele = new Actor(id, "Pepito", "Grillo");
		when(srv.add(ele)).thenReturn(ele);
		mockMvc.perform(post("/api/actores/v1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(ActorDTO.from(ele)))
			)
			.andExpect(status().isCreated())
	        .andExpect(header().string("Location", "http://localhost/api/actores/v1/1"))
	        .andDo(print())
	        ;
	}

//	@Test
//	void testDarPremio() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testUpdate() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDelete() {
//		fail("Not yet implemented");
//	}

}
