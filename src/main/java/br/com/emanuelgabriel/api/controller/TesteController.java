package br.com.emanuelgabriel.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import br.com.emanuelgabriel.api.service.TesteAppService;
import br.com.emanuelgabriel.api.service.TesteService;
import br.com.emanuelgabriel.api.service.TokenService;

/**
 * 
 * @author emanuel.sousa
 *
 */

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class TesteController {

	private static final Logger LOG = LoggerFactory.getLogger(TesteController.class);

	@Autowired
	private TesteService testeService;

	@Autowired
	private TesteAppService service;

	@Autowired
	private TokenService tokenService;

	@GetMapping(value = "/arrecadacao-qr-code", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> testeRestTemplate(@RequestParam(value = "numeroConvenio") String numeroConvenio,
			@RequestParam(value = "codigoGuiaRecebimento") String codigoGuiaRecebimento) {
		LOG.info("GET /api/arrecadacao-qr-code - {};{}", numeroConvenio, codigoGuiaRecebimento);
		var resposta = testeService.buscarArrecadacaoQrCode(numeroConvenio, codigoGuiaRecebimento);
		return ResponseEntity.ok().body(resposta);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> teste(
			@RequestParam(value = "numeroConvenio") String numeroConvenio,
			@RequestParam(value = "codigoGuiaRecebimento") String codigoGuiaRecebimento) throws Exception {
		LOG.info("GET /api");
		
		try {
			String resposta = service.buscarArrecadacao(numeroConvenio, codigoGuiaRecebimento);
			return ResponseEntity.ok().body(resposta);
		} catch (RestClientException e) {
			LOG.info("Erro: {}", e.getMessage());
			throw new Exception("FALHA CLIENT");
		} catch (Exception e) {
			throw new Exception("FALHA TOKEN");
		}
	}

	@GetMapping(value = "token-invalido", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> tokenInvalido() throws Exception {
		LOG.info("GET /api/token-invalido");
		
		try {
			
			// nas exceções GET/POST/PUT
			tokenService.setTokenValido(false);
			return ResponseEntity.ok().body(tokenService.getToken());
			
		} catch (RestClientException e) {
			LOG.info("Erro: {}", e.getMessage());
			throw new Exception("FALHA CLIENT");
		} catch (Exception e) {
			throw new Exception("FALHA TOKEN");
		}
	}

}
