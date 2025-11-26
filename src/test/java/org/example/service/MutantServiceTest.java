package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MutantServiceTest {

	@Mock
	private MutantDetector mutantDetector;

	@Mock
	private DnaRecordRepository dnaRecordRepository;

	@InjectMocks
	private MutantService mutantService;

	@Test
	@DisplayName("Guarda y retorna mutante cuando el detector devuelve true")
	void testAnalyzeDnaMutant() {
		String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
		when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
		when(mutantDetector.isMutant(dna)).thenReturn(true);

		boolean result = mutantService.analyzeDna(dna);

		assertTrue(result);
		ArgumentCaptor<DnaRecord> captor = ArgumentCaptor.forClass(DnaRecord.class);
		verify(dnaRecordRepository).save(captor.capture());
		assertTrue(captor.getValue().isMutant());
		verify(mutantDetector).isMutant(dna);
	}

	@Test
	@DisplayName("Guarda y retorna humano cuando el detector devuelve false")
	void testAnalyzeDnaHuman() {
		String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "ACCCTA", "TCACTG"};
		when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
		when(mutantDetector.isMutant(dna)).thenReturn(false);

		boolean result = mutantService.analyzeDna(dna);

		assertFalse(result);
		ArgumentCaptor<DnaRecord> captor = ArgumentCaptor.forClass(DnaRecord.class);
		verify(dnaRecordRepository).save(captor.capture());
		assertFalse(captor.getValue().isMutant());
		verify(mutantDetector).isMutant(dna);
	}

	@Test
	@DisplayName("Retorna cache sin invocar al detector cuando el hash ya existe")
	@SuppressWarnings("unchecked")
	void testCacheHit() {
		String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "ACCCTA", "TCACTG"};
		DnaRecord cached = DnaRecord.builder()
				.dnaHash("cached-hash")
				.isMutant(true)
				.build();
		when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.of(cached));

		boolean result = mutantService.analyzeDna(dna);

		assertTrue(result);
		verify(mutantDetector, never()).isMutant(any());
		verify(dnaRecordRepository, never()).save(any());
	}
}
