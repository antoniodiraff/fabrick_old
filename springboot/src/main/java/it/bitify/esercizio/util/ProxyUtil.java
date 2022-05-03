package it.bitify.esercizio.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.bitify.esercizio.controller.AccountController;
import it.bitify.esercizio.dto.SandBoxBaseResponse;
import it.bitify.esercizio.exception.BadRequestException;
import javassist.tools.web.BadHttpRequest;

@Component
public class ProxyUtil {

	Logger logger = LoggerFactory.getLogger(ProxyUtil.class);

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

	@Value("${fabrick.moneytransfer}")
	public String moneytransferApi;

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
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 * @throws BadHttpRequest
	 */
	public ResponseEntity<SandBoxBaseResponse> restCall(String api, HttpMethod httpMethod, Map<String, String> params,
			Object request) {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(AppConstants.AUTHSCHEMA, authschema);
		headers.set(AppConstants.APIKEY, apikey);

		String url = baseurl + api;

		if (params == null) {
			params = new HashMap<>();
		} else {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				String key = entry.getKey();
				url = UriComponentsBuilder.fromHttpUrl(url).queryParam(key, "{" + key + "}").encode().toUriString();
			}
		}
		try {
			logger.debug("Request: " + api);
			ResponseEntity<SandBoxBaseResponse> responseEntity;
			if (request != null) {
				HttpEntity<Object> requestEntity = new HttpEntity<>(request, headers);
				responseEntity = restTemplate.exchange(url, httpMethod, requestEntity, SandBoxBaseResponse.class);
			} else {
				responseEntity = restTemplate.exchange(url, httpMethod, new HttpEntity<>("parameters", headers),
						SandBoxBaseResponse.class, params);
			}
			return responseEntity;
		} catch (HttpStatusCodeException e) {
			logger.error("Error requesting: " + api);
			return exception(e);
		}
		catch (RestClientException e) {
			throw new BadRequestException(e.getMessage(), e.getCause());
		}
	}

	/**
	 * 
	 * @param e
	 * @return
	 */
	private ResponseEntity<SandBoxBaseResponse> exception(HttpStatusCodeException e) {
		SandBoxBaseResponse response = new SandBoxBaseResponse();
		String bodyString = e.getResponseBodyAsString();
		ObjectMapper mapper = new ObjectMapper();
		try {
			response = mapper.readValue(bodyString, SandBoxBaseResponse.class);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		return new ResponseEntity<SandBoxBaseResponse>(response, e.getStatusCode());
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
		return accountApi + accountId.toString();
	}

	/**
	 * Costruisce l'URL per il bonifico.
	 * 
	 * @param accountId
	 * @return
	 */
	public String buildMoneyTransferUrl(Long accountId) {
		return accountApi + String.valueOf(accountId) + moneytransferApi;
	}
}
