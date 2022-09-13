package br.com.emanuelgabriel.api;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.emanuelgabriel.api.service.GuardarTokenService;

/**
 * 
 * @author emanuel.sousa
 *
 */

@Component
public class TesteAppService {
	
	private static final Logger LOG = LoggerFactory.getLogger(TesteAppService.class);

	public static final String AUTHORIZATION = "Authorization";

	@Value("${url.base.api.bb.sandbox}")
	private String urlBaseApiBbSandBox;

	@Autowired
	private TokenManager tokenManager;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
    private GuardarTokenService service;

	/**
	 * Retorna o Token
	 * @return String
	 */
	public String buscarArrecadacao() {
		
		String token = tokenManager.getAccessToken();
		
		Map<String, String> mapTokenGuardado = service.guardarTokenDaRequisicao();
		if (mapTokenGuardado.containsValue(token)) {
			LOG.info("Token válido!");
		} else {
			LOG.info("Token inválido!");
		}

		String url = urlBaseApiBbSandBox + "/arrecadacao-qrcodes?gw-dev-app-key=d27b37790cffab601363e17d00050356b901a5b8&numeroConvenio=62191&codigoGuiaRecebimento=83660000000199800053846101173758000000000018";
		LOG.info("URL: {}", url);

		HttpHeaders headers = new HttpHeaders();
		headers.add(AUTHORIZATION, "Bearer " + token);
		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<String> response = null;
		
		try {
			
			response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
			LOG.info("Response: {}", response);
			return response.getBody();

		} catch (RestClientException e) {
			if (e.getMessage().contains("503 Service Unavailable: ")) {
				LOG.info("Serviço indisponível - Service Unavailable");
			}
		}
		
		return null;
		
	}

}
