package br.com.emanuelgabriel.api.mapper.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Oauth2ResponseDTO {

	private String clientId;
	private String clientSecret;
	private String authorizationGrantType;
	private String scope;
	private String providerAppTokenUri;

}
