package br.com.emanuelgabriel.api.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.emanuelgabriel.api.config.PropriedadesOauthConfig;
import br.com.emanuelgabriel.api.mapper.response.AccessTokenResponseDTO;

/**
 * 
 * @author emanuel.sousa
 *
 */
@Service
public class AccessTokenService {
	
	private static final Logger LOG = LoggerFactory.getLogger(AccessTokenService.class);

	@Autowired
	private PropriedadesOauthConfig config;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
    private GuardarTokenService guardarTokenService;

	
	/**
	 * Buscar o access_token
	 * @return
	 */
	public String getAcessToken() {
		
		HttpHeaders httpHeaders = null;
		Map<String, String> mapToken = null;
		AccessTokenResponseDTO accessTokenResponseDTO = null;
		
		try {
			
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("client_id", config.oauth2ResponseDTO().getClientId());
		map.add("client_secret", config.oauth2ResponseDTO().getClientSecret());
		map.add("grant_type", config.oauth2ResponseDTO().getAuthorizationGrantType());
		map.add("scope", config.oauth2ResponseDTO().getScope());
		
		
		httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("Authorization", config.headerResponseDTO().getHeaderBasicAuthorization());
		var httpEntity = new HttpEntity<MultiValueMap<String, String>>(map, httpHeaders);

		accessTokenResponseDTO = restTemplate.postForEntity(config.providerAppTokenUri().getTokenUri().concat("?developer_application_key=".concat(config.headerResponseDTO().getChaveAppDeveloper())), httpEntity , AccessTokenResponseDTO.class).getBody();
		LOG.info("Response: {}", accessTokenResponseDTO);
		
	    mapToken = guardarTokenService.guardarTokenDaRequisicao();
	    mapToken.put("token", accessTokenResponseDTO.getAccessToken());
	    
		} catch (RestClientException e) {
			LOG.info("Erro: {}", e.getMessage());
		}
		
		return accessTokenResponseDTO.getAccessToken();
	}
	
}
