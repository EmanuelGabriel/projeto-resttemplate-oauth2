package br.com.emanuelgabriel.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author emanuel.sousa
 *
 */

@Service
public class TesteService {

	private static final Logger LOG = LoggerFactory.getLogger(TesteService.class);

	@Value("${url.base.api.bb.sandbox}")
	private String urlBaseApiBbSandBox;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private AccessTokenService accessTokenService;

	/**
	 * EXEMPLO DE REQUISIÇÃO: /arrecadacao-qrcodes?gw-dev-app-key=d27b37790cffab601363e17d00050356b901a5b8&=62191&codigoGuiaRecebimento=83660000000199800053846101173758000000000018
	 * @return String
	 */
	public String buscarArrecadacaoQrCode(String numeroConvenio, String codigoGuiaRecebimento) {
		
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

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessTokenService.getAcessToken());
		HttpEntity<Void> request = new HttpEntity<>(headers);
		
		ResponseEntity<String> response = null;
		
		try {

			response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
			LOG.info("Response: {}", response.getBody());

		} catch (RestClientException e) {
			LOG.info("Erro: {}", e.getMessage());
		}

		return response.getBody();
		
	}
	
	
	
	
}
