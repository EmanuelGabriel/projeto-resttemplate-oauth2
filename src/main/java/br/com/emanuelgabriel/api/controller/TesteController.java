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

import br.com.emanuelgabriel.api.service.TesteService;

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

	@GetMapping(value = "/arrecadacao-qr-code", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> testeRestTemplate(@RequestParam(value = "numeroConvenio") String numeroConvenio,
			@RequestParam(value = "codigoGuiaRecebimento") String codigoGuiaRecebimento) {
		LOG.info("GET /teste/arrecadacao-qr-code - {};{}", numeroConvenio, codigoGuiaRecebimento);
		var resposta = testeService.buscarArrecadacaoQrCode(numeroConvenio, codigoGuiaRecebimento);
		return ResponseEntity.ok().body(resposta);
	}

}
