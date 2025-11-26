package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.DnaRequest;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.example.service.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Mutant Detection", description = "Endpoints para deteccion de mutantes y estadisticas")
public class MutantController {

	private final MutantService mutantService;
	private final StatsService statsService;

	@PostMapping("/mutant")
	@Operation(summary = "Detecta si un ADN es mutante")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ADN mutante detectado"),
			@ApiResponse(responseCode = "403", description = "ADN humano"),
			@ApiResponse(responseCode = "400", description = "Solicitud invalida", content = @Content(schema = @Schema(hidden = true)))
	})
	public ResponseEntity<Void> analyze(@Valid @RequestBody DnaRequest request) {
		boolean isMutant = mutantService.analyzeDna(request.getDna());
		if (isMutant) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@GetMapping("/stats")
	@Operation(summary = "Obtiene estadisticas de deteccion")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Estadisticas calculadas")
	})
	public ResponseEntity<StatsResponse> stats() {
		return ResponseEntity.ok(statsService.getStats());
	}
}
