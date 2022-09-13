package br.com.emanuelgabriel.api.mapper.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeaderResponseDTO {

	private String chaveAppDeveloper;
	private String headerBasicAuthorization;

}
