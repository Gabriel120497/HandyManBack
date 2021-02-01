package com.gabriel.handyMan.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.handyMan.models.dto.ReporteDTO;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ReporteRestControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	void testCreate() throws Exception {
		ReporteDTO newReporte = new ReporteDTO();
		newReporte.setIdTecnico(1209L);
		newReporte.setIdServicio(2L);
		newReporte.setFechaInicio("2021-02-01T12:45");
		newReporte.setFechaFin("2021-02-01T15:45");

		ObjectMapper objectMapper = new ObjectMapper();
		String jString = objectMapper.writeValueAsString(newReporte);
		
		System.out.println(jString);
		
		mockMvc.perform(post("/api/reportes/").content(jString).contentType("application/json;charset=UTF-8")
				.characterEncoding("utf-8")).andExpect(status().isCreated());
	}

}
