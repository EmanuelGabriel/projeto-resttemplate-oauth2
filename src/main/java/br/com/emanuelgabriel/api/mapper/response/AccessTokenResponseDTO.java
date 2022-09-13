package br.com.emanuelgabriel.api.mapper.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenResponseDTO {

	@JsonProperty(value = "access_token")
	private String accessToken;

	@JsonProperty(value = "token_type")
	private String tokenType;

	@JsonProperty(value = "expires_in")
	private int expiresIn;

}
