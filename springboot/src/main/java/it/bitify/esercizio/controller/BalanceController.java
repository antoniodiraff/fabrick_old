package it.bitify.esercizio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import antlr.MismatchedCharException;
import io.swagger.annotations.ApiOperation;
import it.bitify.esercizio.dto.SandBoxBaseResponse;
import it.bitify.esercizio.util.AppConstants;
import it.bitify.esercizio.util.ProxyUtil;

/*******************************************************************************************
 * Created by A. Di Raffaele The Balance
 ******************************************************************************************/
@RestController
@RequestMapping("/api/balance")
public class BalanceController {

	@Autowired
	ProxyUtil proxyUtil;

	/**
	 * 
	 * @param accountId
	 * @return
	 */
	@ApiOperation(value = "Retrieve balance info. Account id to test: 14537780")
	@GetMapping("/sandbox/{accountId}")
	public ResponseEntity<SandBoxBaseResponse> getBalanceByAccountId(@PathVariable String accountId) {
		try {
			return proxyUtil.restCall(proxyUtil.buildBalanceUrl(Long.parseLong(accountId)), HttpMethod.GET, null, null);
		} catch (NumberFormatException e) {
			return new ResponseEntity<SandBoxBaseResponse>(new SandBoxBaseResponse().setError(e.getMessage()), HttpStatus.BAD_REQUEST);		
		}
	}
}