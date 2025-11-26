package org.example.service;

import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {

	@Mock
	private DnaRecordRepository dnaRecordRepository;

	@InjectMocks
	private StatsService statsService;

	@Test
	@DisplayName("Calcula ratio estandar con mutantes y humanos")
	void testRatioStandard() {
		when(dnaRecordRepository.countByIsMutant(true)).thenReturn(40L);
		when(dnaRecordRepository.countByIsMutant(false)).thenReturn(100L);

		StatsResponse response = statsService.getStats();

		assertEquals(40L, response.getCountMutantDna());
		assertEquals(100L, response.getCountHumanDna());
		assertEquals(0.4, response.getRatio(), 0.0001);
	}

	@Test
	@DisplayName("Maneja division por cero cuando no hay humanos")
	void testRatioDivisionByZero() {
		when(dnaRecordRepository.countByIsMutant(true)).thenReturn(10L);
		when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L);

		StatsResponse response = statsService.getStats();

		assertEquals(10L, response.getCountMutantDna());
		assertEquals(0L, response.getCountHumanDna());
		assertEquals(0.0, response.getRatio(), 0.0001);
	}

	@Test
	@DisplayName("Retorna ratio cero cuando no hay registros")
	void testZeroRecords() {
		when(dnaRecordRepository.countByIsMutant(true)).thenReturn(0L);
		when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L);

		StatsResponse response = statsService.getStats();

		assertEquals(0L, response.getCountMutantDna());
		assertEquals(0L, response.getCountHumanDna());
		assertEquals(0.0, response.getRatio(), 0.0001);
	}
}
