package br.com.emanuelgabriel.api;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import br.com.emanuelgabriel.api.config.PropriedadesOauthConfig;
import br.com.emanuelgabriel.api.service.GuardarTokenService;



/**
 * 
 * @author emanuel.sousa
 *
 */

@Component
public class TokenManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(TokenManager.class);

	@Value("${client.registration.name}")
	private String clientRegistrationName;

	@Autowired
	private PropriedadesOauthConfig config;
	
	@Autowired
    private GuardarTokenService guardarTokenService;

	private final OAuth2AuthorizedClientManager authorizedClientManager;

	public TokenManager(OAuth2AuthorizedClientManager authorizedClientManager) {
		this.authorizedClientManager = authorizedClientManager;
	}

	  /**
	   * Pegar o Token
	   * @return String
	   */
	  public String getAccessToken() {
	    var authorizeRequest = OAuth2AuthorizeRequest
	    			.withClientRegistrationId(clientRegistrationName)
	                .principal(config.oauth2ResponseDTO().getClientId())
	                .build();

	    var authorizedClient = this.authorizedClientManager.authorize(authorizeRequest);
	    LOG.info("{}", authorizedClient.getAccessToken().getTokenValue());
	    
	    Map<String, String> salvarToken = guardarTokenService.guardarTokenDaRequisicao();
	    salvarToken.put("token", authorizedClient.getAccessToken().getTokenValue());

	    OAuth2AccessToken accessToken = authorizedClient.getAccessToken();

	    return accessToken.getTokenValue();
	  }

}
