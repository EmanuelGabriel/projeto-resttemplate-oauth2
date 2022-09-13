package br.com.emanuelgabriel.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.emanuelgabriel.api.mapper.response.HeaderResponseDTO;
import br.com.emanuelgabriel.api.mapper.response.Oauth2ResponseDTO;
import br.com.emanuelgabriel.api.mapper.response.ProviderAppTokenUri;

/**
 * 
 * @author emanuel.sousa
 *
 */

@Configuration
public class PropriedadesOauthConfig {

	@Bean
	@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.app")
	public Oauth2ResponseDTO oauth2ResponseDTO() {
		return new Oauth2ResponseDTO();
	}

	@Bean(value = "providerAppTokenUri")
	@ConfigurationProperties(prefix = "spring.security.oauth2.client.provider.app")
	public ProviderAppTokenUri providerAppTokenUri() {
		return new ProviderAppTokenUri();
	}

	@Bean(name = "HeaderApp")
	@ConfigurationProperties(prefix = "header.app")
	public HeaderResponseDTO headerResponseDTO() {
		return new HeaderResponseDTO();
	}
}
