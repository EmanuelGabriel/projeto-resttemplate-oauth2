package br.com.emanuelgabriel.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author emanuel.sousa
 *
 */

@Configuration
public class RestTemplateConfig {

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		// restTemplate.setInterceptors(Collections.singletonList(new
		// RequestResponseHandlerInterceptorResttemplate()));
		return restTemplate;

	}

}
