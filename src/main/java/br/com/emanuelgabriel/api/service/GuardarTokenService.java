package br.com.emanuelgabriel.api.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuardarTokenService {

	@Autowired
	private GuardarToken guardarToken;

	public Map<String, String> guardarTokenDaRequisicao() {
		return guardarToken.salvarToken();
	}

}
