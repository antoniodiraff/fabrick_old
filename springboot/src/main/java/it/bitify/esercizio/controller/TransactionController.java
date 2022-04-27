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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionTimedOutException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import antlr.MismatchedCharException;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.bitify.esercizio.service.TransactionService;
import it.bitify.esercizio.model.Account;
import it.bitify.esercizio.model.Transaction;
import it.bitify.esercizio.dto.PagedResponse;
import it.bitify.esercizio.dto.SandBoxBaseResponse;
import it.bitify.esercizio.exception.BadRequestException;
import it.bitify.esercizio.dto.ApiResponse;
import it.bitify.esercizio.util.AppConstants;
import it.bitify.esercizio.util.ProxyUtil;
import springfox.documentation.annotations.ApiIgnore;


/*******************************************************************************************
 * Created by A. Di Raffaele 
 * The Transaction
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
   public PagedResponse<Transaction> getTransactions( @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                               				  @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
														      @RequestParam(value = "sortdir", defaultValue = "1") int sortdir,
														      @RequestParam(value = "sortfield", defaultValue = "transactionId") String sortfield,
   															  @RequestParam(value = "searchstring", defaultValue = "") String searchstring) {
   																  
       return transactionService.getAllPaged(page, size, sortdir,sortfield,searchstring);
   }
   
   @GetMapping("/{id}")
   public Transaction getTransactionById(@PathVariable Long id) {
       return transactionService.findById(id).get();
   }
   
   @PutMapping
   public ResponseEntity<Object> 
      updateTransaction(@RequestBody Transaction transaction) {
      
	   transactionService.updateTransaction(transaction);
      return ResponseEntity.ok(new ApiResponse(true, "Transaction is updated successsfully"));
   }
   
   @DeleteMapping("/{id}")
   public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
	   transactionService.deleteTransaction(id);
      return ResponseEntity.ok(new ApiResponse(true, "Transaction is deleted successsfully"));
   }
   
   @PutMapping("/deleteAll")
   public ResponseEntity<Object> 
      deleteTransactions(@RequestBody Transaction[] transactions) {
      
	   transactionService.deleteTransactions(transactions);
      return ResponseEntity.ok(new ApiResponse(true, "Transactions are deleted successsfully"));
   }
   
   @PostMapping
   public ResponseEntity<Object> createTransaction(@RequestBody Transaction transaction) {
	   transactionService.createTransaction(transaction);
      return ResponseEntity.ok(new ApiResponse(true, "Transaction is created successsfully"));
   }
   	
   /**
    * 
    * @param accountId
    * @return
    */
   @ApiOperation(value = "Retrieve transaction info. Account id to test: 14537780")
   @GetMapping("/sandbox/{accountId}")
   public ResponseEntity<Object> getTransactionsByAccountId(@PathVariable Long accountId, 			
//		   @RequestParam(value = "fromAccountingDate", required = true) @DateTimeFormat(pattern="yyyy-MM-dd") Date fromAccountingDate,
//           @RequestParam(value = "toAccountingDate", required = true)  @DateTimeFormat(pattern="yyyy-MM-dd") Date toAccountingDate) {
		   @RequestParam(value = "fromAccountingDate", required = true) String fromAccountingDate,
         @RequestParam(value = "toAccountingDate", required = true)  String toAccountingDate)
        		 throws RestClientException, 
						   BadRequestException, 
						   MismatchedCharException, 
						   TransactionTimedOutException {
		
		Map<String, String> params = new HashMap<>();
		if(fromAccountingDate!=null)
		{
			params.put("fromAccountingDate", fromAccountingDate);
		}
		
		if(fromAccountingDate!=null)
		{
			params.put("toAccountingDate", toAccountingDate);
		}
		

		ResponseEntity<SandBoxBaseResponse> response =  proxyUtil.restCall(proxyUtil.buildTransactionsUrl(accountId), HttpMethod.GET, params); 
		return ResponseEntity.ok(response.getBody()); 
   }
   
}
