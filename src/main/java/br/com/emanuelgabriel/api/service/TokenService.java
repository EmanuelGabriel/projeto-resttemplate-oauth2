package br.com.emanuelgabriel.api.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.emanuelgabriel.api.config.PropriedadesOauthConfig;

/**
 * 
 * @author emanuel.sousa
 *
 */

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TokenService {
	
	private static final Logger LOG = LoggerFactory.getLogger(TokenService.class);

	@Autowired
	private PropriedadesOauthConfig config;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private String token;
	private boolean tokenValido;

	public TokenService() {
	}

	public TokenService(String token) {
		this.token = token;
	}

	public String getToken() throws Exception {
		try {
			
			if (!tokenValido) {
				this.token = String.valueOf(new Date().getTime());
				this.tokenValido = true;
				System.out.println("NOVO TOKEN: " + token);
				
				MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
				map.add("client_id", config.oauth2ResponseDTO().getClientId());
				map.add("client_secret", config.oauth2ResponseDTO().getClientSecret());
				map.add("grant_type", config.oauth2ResponseDTO().getAuthorizationGrantType());
				map.add("scope", config.oauth2ResponseDTO().getScope());
				
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
				httpHeaders.setContentType(MediaType.APPLICATION_JSON);
				httpHeaders.set("Authorization", config.headerResponseDTO().getHeaderBasicAuthorization());
				var httpEntity = new HttpEntity<MultiValueMap<String, String>>(map, httpHeaders);

				String body = restTemplate.postForEntity(config.providerAppTokenUri().getTokenUri().concat("?developer_application_key=".concat(config.headerResponseDTO().getChaveAppDeveloper())), httpEntity , String.class).getBody();
				LOG.info("Body: {}", body);
				
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode jsonNodeBody = objectMapper.readValue(body, JsonNode.class);

				this.token = jsonNodeBody.get("access_token").asText();
				LOG.info("Token: {}", this.token);

			} else {
				LOG.info("TOKEN EXISTENTE: {}", token);
			}

			return token;
			
		} catch (Exception e) {
			this.tokenValido = false;
			throw new Exception("FALHA TOKEN");
		}
	}

	public boolean isTokenValido() {
		return tokenValido;
	}

	public void setTokenValido(boolean tokenValido) {
		this.tokenValido = tokenValido;
	}

}
