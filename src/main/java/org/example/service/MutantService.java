package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MutantService {

	private final MutantDetector mutantDetector;
	private final DnaRecordRepository dnaRecordRepository;

	public boolean analyzeDna(String[] dna) {
		String dnaHash = calculateHash(dna);

		Optional<DnaRecord> existing = dnaRecordRepository.findByDnaHash(dnaHash);
		if (existing.isPresent()) {
			return existing.get().isMutant(); // Reutiliza resultado en cache.
		}

		boolean isMutant = mutantDetector.isMutant(dna);
		DnaRecord record = DnaRecord.builder()
				.dnaHash(dnaHash)
				.isMutant(isMutant)
				.createdAt(LocalDateTime.now())
				.build();
		dnaRecordRepository.save(record);
		return isMutant;
	}

	private String calculateHash(String[] dna) {
		if (dna == null) {
			return "NULL_DNA"; // Hash constante para entradas nulas.
		}
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			for (String row : dna) {
				if (row != null) {
					digest.update(row.getBytes(StandardCharsets.UTF_8));
				} else {
					digest.update((byte) 0); // Marca filas nulas para diferenciar.
				}
			}
			byte[] hashBytes = digest.digest();
			return Base64.getEncoder().encodeToString(hashBytes);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("SHA-256 no disponible", e);
		}
	}
}
