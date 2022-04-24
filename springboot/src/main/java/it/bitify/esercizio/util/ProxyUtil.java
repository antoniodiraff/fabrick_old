package it.bitify.esercizio.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import it.bitify.esercizio.dto.SandBoxBaseResponse;

@Component
public class ProxyUtil {

	@Value("${fabrick.baseurl}")
	public String baseurl;

	@Value("${fabrick.authschema}")
	public String authschema;

	@Value("${fabrick.apikey}")
	public String apikey;
	
	
	public ResponseEntity<SandBoxBaseResponse> restCall(String api, HttpMethod httpMethod) {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Auth-Schema", authschema);
		headers.set("Api-Key", apikey);
		
		String url = baseurl + api; 
		
		try {
			ResponseEntity<SandBoxBaseResponse> responseEntity = restTemplate.exchange(url , httpMethod, new HttpEntity<>("parameters", headers), SandBoxBaseResponse.class);
			return responseEntity;
		}catch (RestClientException e) {
			throw new RestClientException(e.getMessage().substring(e.getMessage().indexOf("["), e.getMessage().length()));	
		}
	}
}
