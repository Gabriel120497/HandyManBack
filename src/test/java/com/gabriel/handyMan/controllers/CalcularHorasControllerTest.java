package com.gabriel.handyMan.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.handyMan.models.entity.Reporte;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CalcularHorasControllerTest {

	   private MockMvc mockMvc;

	    @Autowired
	    private WebApplicationContext context;

	    @BeforeEach
	    public void setUp() {
	        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	    }
	
	@Test
	void test() throws Exception {
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		
		requestParams.add("tecnicoId", String.valueOf(1234L));
		requestParams.add("numeroSemana", "8");
		mockMvc.perform(get("/api/calcularHoras/").params(requestParams).characterEncoding("utf-8")).andExpect(status().isOk());
		//fail("Not yet implemented");
	}

}
