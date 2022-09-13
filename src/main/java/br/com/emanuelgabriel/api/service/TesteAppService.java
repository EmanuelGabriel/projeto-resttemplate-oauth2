package br.com.emanuelgabriel.api.service;

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
	private RestTemplate restTemplate;

	@Autowired
	private TokenService tokenService;

	/**
	 * Retorna o Token
	 *
	 * @return String
	 */
	public String buscarArrecadacao(String numeroConvenio, String codigoGuiaRecebimento) throws Exception {
		LOG.info("Buscar arrecadação qr code - NumeroConvenio: {}; CodigoGuiaRecebimento: {}", numeroConvenio, codigoGuiaRecebimento);
		
		String url = urlBaseApiBbSandBox
				.concat("/arrecadacao-qrcodes?gw-dev-app-key=d27b37790cffab601363e17d00050356b901a5b8")
				.concat("&")
				.concat("numeroConvenio=")
				.concat(numeroConvenio)
				.concat("&")
				.concat("codigoGuiaRecebimento=")
				.concat(codigoGuiaRecebimento);
		LOG.info("URL: {}", url);
		
		ResponseEntity<String> response = null;
		
		try {
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + tokenService.getToken());
			HttpEntity<Void> request = new HttpEntity<>(headers);
			
			response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
			LOG.info("Response: {}", response.getBody());

			return response.getBody();

		} catch (RestClientException e) {
			LOG.info("Erro: {}", e.getMessage());
			throw new Exception("FALHA CLIENT");
		} catch (Exception e) {
			throw new Exception("FALHA TOKEN");
		}

	}

}
