package br.com.emanuelgabriel.api.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;

/**
 * 
 * @author emanuel.sousa
 *
 */

@Configuration
@ApplicationScope
public class GuardarToken {

	Map<String, String> accessToken;

	@Bean
	public Map<String, String> salvarToken() {
		return new HashMap<>();
	}

}
