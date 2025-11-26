package org.example.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

	private final MutantDetector mutantDetector = new MutantDetector();

	@Test
	@DisplayName("Detecta mutante por secuencias horizontales solapadas")
	void testMutantHorizontal() {
		String[] dna = {
				"AAAAAA",
				"TTGCGC",
				"TTGCGC",
				"TTGCGC",
				"TTGCGC",
				"TTGCGC"
		};
		assertTrue(mutantDetector.isMutant(dna));
	}

	@Test
	@DisplayName("Detecta mutante por secuencias verticales solapadas")
	void testMutantVertical() {
		String[] dna = {
				"AGTCGT",
				"AGTCGT",
				"AGTCGT",
				"AGTCGT",
				"AGTCGT",
				"AGTCGT"
		};
		assertTrue(mutantDetector.isMutant(dna));
	}

	@Test
	@DisplayName("Detecta mutante por diagonal principal")
	void testMutantDiagonalPrincipal() {
		String[] dna = {
				"CAGTGA",
				"ACCGTA",
				"ATCCGA",
				"ATGCTA",
				"ATGCCT",
				"ATGCCC"
		};
		assertTrue(mutantDetector.isMutant(dna));
	}

	@Test
	@DisplayName("Detecta mutante por diagonal inversa")
	void testMutantDiagonalInvertida() {
		String[] dna = {
				"ATCGGG",
				"ATGGGT",
				"ATGGCT",
				"AGGCTA",
				"AGGTAA",
				"GAGTAC"
		};
		assertTrue(mutantDetector.isMutant(dna));
	}

	@Test
	@DisplayName("Detecta mutante con multiples secuencias en distintas direcciones")
	void testMutantMultipleSequences() {
		String[] dna = {
				"AAAACT",
				"CGTGCA",
				"TTACTG",
				"AGAAAG",
				"CCCCTA",
				"TCACTG"
		};
		assertTrue(mutantDetector.isMutant(dna));
	}

	@Test
	@DisplayName("Humano sin secuencias repetidas")
	void testHumanNoSequence() {
		String[] dna = {
				"ATCGAT",
				"CGATCG",
				"GATCGA",
				"TCGATC",
				"ATCGAT",
				"CGATCG"
		};
		assertFalse(mutantDetector.isMutant(dna));
	}

	@Test
	@DisplayName("Humano con exactamente una secuencia")
	void testHumanOneSequence() {
		String[] dna = {
				"AAAAGC",
				"ATCGTA",
				"CGATCG",
				"GTCAGT",
				"TACGAT",
				"CGATCG"
		};
		assertFalse(mutantDetector.isMutant(dna));
	}

	@Test
	@DisplayName("Entrada null retorna falso")
	void testNullDna() {
		assertFalse(mutantDetector.isMutant(null));
	}

	@Test
	@DisplayName("Array vacio retorna falso")
	void testEmptyDna() {
		assertFalse(mutantDetector.isMutant(new String[]{}));
	}

	@Test
	@DisplayName("Matriz no cuadrada lanza IllegalArgumentException")
	void testNxM() {
		String[] dna = {"ATGC", "CAGT", "TTAT"};
		assertThrows(IllegalArgumentException.class, () -> mutantDetector.isMutant(dna));
	}

	@Test
	@DisplayName("Caracter invalido lanza IllegalArgumentException")
	void testInvalidData() {
		String[] dna = {
				"ATGCGA",
				"CAGTGC",
				"TTATGT",
				"AGAAGG",
				"CCCXTA",
				"TCACTG"
		};
		assertThrows(IllegalArgumentException.class, () -> mutantDetector.isMutant(dna));
	}

	@Test
	@DisplayName("Fila nula lanza IllegalArgumentException")
	void testNullRow() {
		String[] dna = {
				"ATGCGA",
				null,
				"TTATGT",
				"AGAAGG",
				"CCCCTA",
				"TCACTG"
		};
		assertThrows(IllegalArgumentException.class, () -> mutantDetector.isMutant(dna));
	}

	@Test
	@DisplayName("Matriz 4x4 minima detecta mutante")
	void testSmallMatrix() {
		String[] dna = {
				"AAAA",
				"CCCC",
				"TTTT",
				"GGGG"
		};
		assertTrue(mutantDetector.isMutant(dna));
	}

	@Test
	@DisplayName("Matriz 3x3 no genera secuencias por tamaño insuficiente")
	void testTooSmallMatrix() {
		String[] dna = {
				"ATC",
				"CGA",
				"GAT"
		};
		assertFalse(mutantDetector.isMutant(dna));
	}

	@Test
	@DisplayName("Borde: matriz grande 100x100 sin secuencias")
	void testLargeMatrix() {
		int size = 100;
		String[] dna = new String[size];
		char[] bases = {'A', 'T', 'C', 'G'};
		for (int i = 0; i < size; i++) {
			char[] row = new char[size];
			for (int j = 0; j < size; j++) {
				row[j] = bases[(i * 2 + j) % bases.length]; // Patrón determinista que evita 4 iguales consecutivas
			}
			dna[i] = new String(row);
		}
		long start = System.nanoTime();
		boolean result = mutantDetector.isMutant(dna);
		long duration = System.nanoTime() - start;
		assertFalse(result, "No debe detectar mutantes en matriz grande. Tiempo(ns): " + duration);
	}
}
