package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsService {

	private final DnaRecordRepository dnaRecordRepository;

	public StatsResponse getStats() {
		long mutantCount = dnaRecordRepository.countByIsMutant(true);
		long humanCount = dnaRecordRepository.countByIsMutant(false);
		double ratio = humanCount == 0 ? 0.0 : (double) mutantCount / (double) humanCount;

		return StatsResponse.builder()
				.countMutantDna(mutantCount)
				.countHumanDna(humanCount)
				.ratio(ratio)
				.build();
	}
}
