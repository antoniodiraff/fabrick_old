package it.bitify.esercizio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import it.bitify.esercizio.dto.MoneyTransferRequest;
import it.bitify.esercizio.dto.SandBoxBaseResponse;
import it.bitify.esercizio.util.ProxyUtil;

@RestController
@RequestMapping("/api/moneytransfer")
public class MoneyTransferController {
	@Autowired
	ProxyUtil proxyUtil;

	/**
	 * 
	 * @param accountId
	 * @return
	 */
	@ApiOperation(value = "Retrieve balance info. Account id to test: 14537780")
	@PostMapping("/sandbox/{accountId}")
	public ResponseEntity<SandBoxBaseResponse> moneyTransfer(@PathVariable String accountId,
			@RequestBody MoneyTransferRequest moneyTransferRequest) {
		try {
			return proxyUtil.restCall(proxyUtil.buildMoneyTransferUrl(Long.parseLong(accountId)), HttpMethod.POST, null,
					moneyTransferRequest);
		} catch (NumberFormatException e) {
			return new ResponseEntity<SandBoxBaseResponse>(new SandBoxBaseResponse().setError(e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}
}
