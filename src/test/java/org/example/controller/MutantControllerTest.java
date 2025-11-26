package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.example.service.StatsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MutantController.class)
class MutantControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private MutantService mutantService;

	@MockBean
	private StatsService statsService;

	@Test
	@DisplayName("POST /mutant retorna 200 cuando es mutante")
	void testMutantReturns200() throws Exception {
		when(mutantService.analyzeDna(any())).thenReturn(true);

		String body = "{\"dna\":[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}";

		mockMvc.perform(post("/mutant")
						.contentType(MediaType.APPLICATION_JSON)
						.content(body))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("POST /mutant retorna 403 cuando es humano")
	void testHumanReturns403() throws Exception {
		when(mutantService.analyzeDna(any())).thenReturn(false);

		String body = "{\"dna\":[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"ACCCTA\",\"TCACTG\"]}";

		mockMvc.perform(post("/mutant")
						.contentType(MediaType.APPLICATION_JSON)
						.content(body))
				.andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("POST /mutant retorna 400 cuando el DNA es invalido")
	void testInvalidDnaReturns400() throws Exception {
		String body = "{\"dna\":[\"ATGZGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}";

		mockMvc.perform(post("/mutant")
						.contentType(MediaType.APPLICATION_JSON)
						.content(body))
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("GET /stats retorna 200 y el JSON esperado")
	void testStatsReturns200() throws Exception {
		StatsResponse stats = StatsResponse.builder()
				.countMutantDna(40)
				.countHumanDna(100)
				.ratio(0.4)
				.build();
		when(statsService.getStats()).thenReturn(stats);

		mockMvc.perform(get("/stats")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.count_mutant_dna", is(40)))
				.andExpect(jsonPath("$.count_human_dna", is(100)))
				.andExpect(jsonPath("$.ratio", is(0.4)));
	}
}
