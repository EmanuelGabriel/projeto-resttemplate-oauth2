package br.com.emanuelgabriel.api.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.ObjectUtils;

/**
 * 
 * @author emanuel.sousa
 *
 */
public class RequestResponseHandlerInterceptorResttemplate implements ClientHttpRequestInterceptor {

	private static final Logger LOG = LoggerFactory.getLogger(RequestResponseHandlerInterceptorResttemplate.class);

	private static final String AUTHORIZATION = "Authorization";

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {


		ClientHttpResponse response = execution.execute(request, body);
		if (HttpStatus.UNAUTHORIZED == response.getStatusCode()) {
			String accessToken = "";
			if (!ObjectUtils.isEmpty(accessToken)) {
				request.getHeaders().remove(AUTHORIZATION);
				request.getHeaders().add(AUTHORIZATION, accessToken);
				response = execution.execute(request, body);
			}
		}
		return response;
	}

}
