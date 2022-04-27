package it.bitify.esercizio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import it.bitify.esercizio.dto.SandBoxBaseResponse;
import it.bitify.esercizio.util.AppConstants;
import it.bitify.esercizio.util.ProxyUtil;

/*******************************************************************************************
 * Created by A. Di Raffaele 
 * The Balance
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
	   public ResponseEntity<Object> getBalanceByAccountId(@PathVariable Long accountId) {
			ResponseEntity<SandBoxBaseResponse> response =  proxyUtil.restCall(proxyUtil.buildBalanceUrl(accountId), HttpMethod.GET); 
		  return ResponseEntity.ok(response.getBody().get(AppConstants.PAYLOAD)); 
	   }
}
