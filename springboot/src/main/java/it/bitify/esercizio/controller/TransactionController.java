/**************************************************************************
*
* Copyright 2022 (C) Bitify s.r.l.
*
* Created on  : 2022-04-24
* Author      : A. Di Raffaele
* Project Name: esercizio 
* Package     : controller
* File Name   : TransactionController.java
*
*-----------------------------------------------------------------------------
* Revision History (Release )
*-----------------------------------------------------------------------------
* VERSION     DESCRIPTION OF CHANGE
*-----------------------------------------------------------------------------
** --/1.0  |  Initial Create.
**---------|------------------------------------------------------------------
***************************************************************************/
package it.bitify.esercizio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.security.access.prepost.PreAuthorize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.bitify.esercizio.service.TransactionService;
import it.bitify.esercizio.model.Transaction;
import it.bitify.esercizio.dto.PagedResponse;
import it.bitify.esercizio.dto.SandBoxBaseResponse;
import it.bitify.esercizio.dto.ApiResponse;
import it.bitify.esercizio.util.AppConstants;
import it.bitify.esercizio.util.ProxyUtil;

/*******************************************************************************************
 * Created by A. Di Raffaele The Transaction
 ******************************************************************************************/
@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

	Logger logger = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	TransactionService transactionService;

	@Autowired
	ProxyUtil proxyUtil;

	private final ModelMapper modelMapper = new ModelMapper();

	@GetMapping("/all")
	public ResponseEntity<Object> getAllTransactions() {
		return new ResponseEntity<>(transactionService.getAll(), HttpStatus.OK);
	}

	@GetMapping
	public PagedResponse<Transaction> getTransactions(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "sortdir", defaultValue = "1") int sortdir,
			@RequestParam(value = "sortfield", defaultValue = "transactionId") String sortfield,
			@RequestParam(value = "searchstring", defaultValue = "") String searchstring) {

		return transactionService.getAllPaged(page, size, sortdir, sortfield, searchstring);
	}

	@GetMapping("/{id}")
	public Transaction getTransactionById(@PathVariable Long id) {
		return transactionService.findById(id).get();
	}

	@PutMapping
	public ResponseEntity<Object> updateTransaction(@RequestBody Transaction transaction) {

		transactionService.updateTransaction(transaction);
		return ResponseEntity.ok(new ApiResponse(true, "Transaction is updated successsfully"));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		transactionService.deleteTransaction(id);
		return ResponseEntity.ok(new ApiResponse(true, "Transaction is deleted successsfully"));
	}

	@PutMapping("/deleteAll")
	public ResponseEntity<Object> deleteTransactions(@RequestBody Transaction[] transactions) {

		transactionService.deleteTransactions(transactions);
		return ResponseEntity.ok(new ApiResponse(true, "Transactions are deleted successsfully"));
	}

	@PostMapping
	public ResponseEntity<Object> createTransaction(@RequestBody Transaction transaction) {
		transactionService.createTransaction(transaction);
		return ResponseEntity.ok(new ApiResponse(true, "Transaction is created successsfully"));
	}
	
	@PostMapping("/insert")
	public ResponseEntity<Object> createTransactions(@RequestBody Transaction[] transactions) {
		transactionService.createTransactions(transactions);
		return ResponseEntity.ok(new ApiResponse(true, "Transactions are created successsfully"));
	}
	/**
	 * 
	 * @param accountId
	 * @return
	 */
	@ApiOperation(value = "Retrieve transaction info. Account id to test: 14537780")
	@GetMapping("/sandbox/{accountId}")
	public ResponseEntity<?> getTransactionsByAccountId(@PathVariable Long accountId,
			@RequestParam(value = "fromAccountingDate", required = true) String fromAccountingDate,
			@RequestParam(value = "toAccountingDate", required = true) String toAccountingDate) {

		Map<String, String> params = new HashMap<>();
		if (fromAccountingDate != null) {
			params.put("fromAccountingDate", fromAccountingDate);
		}

		if (fromAccountingDate != null) {
			params.put("toAccountingDate", toAccountingDate);
		}
		ResponseEntity<SandBoxBaseResponse> result = proxyUtil.restCall(proxyUtil.buildTransactionsUrl(accountId),
				HttpMethod.GET, params, null);
		if(result.getBody().get(AppConstants.ERROR)!=null) {
			return result;
		}
		Collection<Transaction> transactionList = getTransactionList(result);
			return ResponseEntity.ok(new SandBoxBaseResponse().setPayload(transactionList));
	}

	/**
	 * 
	 * @param result
	 * @return
	 */
	private Collection<Transaction> getTransactionList(ResponseEntity<SandBoxBaseResponse> result) {
		LinkedHashMap<String, Object> payload = (LinkedHashMap<String, Object>) result.getBody().get(AppConstants.PAYLOAD);
		Collection<Transaction> transactionList = new ArrayList<Transaction>();
		if (!payload.isEmpty()) {
			List<LinkedHashMap<String, Object>> transactions = (List<LinkedHashMap<String, Object>>) payload.get(AppConstants.LIST);
			transactionList = transactions.stream().map(e -> modelMapper.map(e, Transaction.class)).collect(Collectors.toList());
		}
		return transactionList;
	}

}
