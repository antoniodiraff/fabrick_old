package it.bitify.esercizio.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.util.UriComponentsBuilder;

import ch.qos.logback.classic.Logger;
import it.bitify.esercizio.dto.SandBoxBaseResponse;
import it.bitify.esercizio.exception.BadRequestException;
import it.bitify.esercizio.exception.ResourceNotFoundException;
import javassist.tools.web.BadHttpRequest;

@Component
public class ProxyUtil {

	@Value("${fabrick.baseurl}")
	public String baseurl;

	@Value("${fabrick.authschema}")
	public String authschema;

	@Value("${fabrick.apikey}")
	public String apikey;
	
	@Value("${fabrick.balance}")
	public String balanceApi;
	
	@Value("${fabrick.transactions}")
	public String transactionsApi;
	
	@Value("${fabrick.account}")
	public String accountApi;
	
	
	/**
	 * 
	 * @param api
	 * @param httpMethod
	 * @param params 
	 * @param toAccountingDate 
	 * @param fromAccountingDate 
	 * @return
	 * @throws BadHttpRequest 
	 */
	public ResponseEntity<SandBoxBaseResponse> restCall(String api, HttpMethod httpMethod, Map<String, String> params) {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Auth-Schema", authschema);
		headers.set("Api-Key", apikey);
		
		String url = baseurl + api; 
		if(params==null) {
			params = new HashMap<>();
		}else {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				String key = entry.getKey();
				url = UriComponentsBuilder.fromHttpUrl(url).queryParam(key, "{"+key+"}").encode().toUriString();
			}
		}

		try {
			ResponseEntity<SandBoxBaseResponse> responseEntity = restTemplate.exchange(url , httpMethod, new HttpEntity<>("parameters", headers), SandBoxBaseResponse.class, params);
			return responseEntity;
		}catch (RestClientException e) {
			throw new RestClientException(e.getMessage().substring(e.getMessage().indexOf("["), e.getMessage().length()));	
		}catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage().substring(e.getMessage().indexOf("["), e.getMessage().length()), url, e);	
		}catch (BadRequestException e) {
			throw new BadRequestException(e.getMessage()); 
		}catch (MethodArgumentTypeMismatchException e) {
			throw new MethodArgumentTypeMismatchException(e, null, url, null, e);
		}
	}
	
	/**
	 * 
	 * Costruisce l'URL per la lettura del saldo.
	 * 
	 * @param accountId
	 * @return
	 */
	public String buildBalanceUrl(Long accountId) {
		return accountApi + String.valueOf(accountId) + balanceApi;
	}
	
	/**
	 * 
	 * Costruisce l'URL per la lettura delle transazioni.
	 * 
	 * @param accountId
	 * @return
	 */
	public String buildTransactionsUrl(Long accountId) {
		return accountApi + String.valueOf(accountId) + transactionsApi;
	}
	
	/** 
	 * 
	 * Costruisce l'URL per la retrieve dell'Account.
	 * 
	 * @param accountId
	 * @return
	 */
	public String buildAccountUrl(Long accountId) {
		return  accountApi + accountId.toString(); 
	}
}
