package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.validator.ValidDnaSequence;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DnaRequest {

	@NotNull(message = "El ADN no puede ser nulo")
	@NotEmpty(message = "El ADN no puede estar vacio")
	@ValidDnaSequence
	@Schema(description = "Matriz NxN con bases de ADN", example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]")
	private String[] dna;
}
