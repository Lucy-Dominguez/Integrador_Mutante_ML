package org.example.service;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MutantDetector {

	private static final int SEQUENCE_LENGTH = 4;
	private static final Set<Character> VALID_NUCLEOTIDES = Set.of('A', 'T', 'C', 'G');

	/**
	 * Detecta si el ADN es mutante buscando mas de una secuencia de cuatro bases iguales.
	 * Optimizado para cortar pronto y usar espacio O(1).
	 */
	public boolean isMutant(String[] dna) {
		if (dna == null || dna.length == 0) {
			return false; // Falla rapido ante entrada nula o vacia.
		}

		final int n = dna.length;
		char[][] matrix = new char[n][n]; // Acceso O(1) por indices sin overhead de charAt en cada chequeo.

		for (int i = 0; i < n; i++) {
			String row = dna[i];
			if (row == null || row.length() != n) {
				throw new IllegalArgumentException("La matriz ADN debe ser cuadrada (NxN).");
			}
			for (int j = 0; j < n; j++) {
				char base = row.charAt(j);
				if (!VALID_NUCLEOTIDES.contains(base)) {
					throw new IllegalArgumentException("Base de ADN invalida: " + base);
				}
				matrix[i][j] = base;
			}
		}

		int sequenceCount = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				char base = matrix[i][j];
				int rowsRemaining = n - i;
				int colsRemaining = n - j;

				// Horizontal derecha
				if (colsRemaining >= SEQUENCE_LENGTH &&
						base == matrix[i][j + 1] &&
						base == matrix[i][j + 2] &&
						base == matrix[i][j + 3]) {
					sequenceCount++;
					if (sequenceCount > 1) {
						return true; // Corte inmediato al hallar mas de una secuencia.
					}
				}

				// Vertical abajo
				if (rowsRemaining >= SEQUENCE_LENGTH &&
						base == matrix[i + 1][j] &&
						base == matrix[i + 2][j] &&
						base == matrix[i + 3][j]) {
					sequenceCount++;
					if (sequenceCount > 1) {
						return true;
					}
				}

				// Diagonal principal (abajo-derecha)
				if (rowsRemaining >= SEQUENCE_LENGTH && colsRemaining >= SEQUENCE_LENGTH &&
						base == matrix[i + 1][j + 1] &&
						base == matrix[i + 2][j + 2] &&
						base == matrix[i + 3][j + 3]) {
					sequenceCount++;
					if (sequenceCount > 1) {
						return true;
					}
				}

				// Diagonal inversa (abajo-izquierda)
				if (rowsRemaining >= SEQUENCE_LENGTH && j + 1 >= SEQUENCE_LENGTH &&
						base == matrix[i + 1][j - 1] &&
						base == matrix[i + 2][j - 2] &&
						base == matrix[i + 3][j - 3]) {
					sequenceCount++;
					if (sequenceCount > 1) {
						return true;
					}
				}
			}
		}

		return false;
	}
}
