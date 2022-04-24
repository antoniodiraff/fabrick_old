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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.security.access.prepost.PreAuthorize;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.bitify.esercizio.service.TransactionService;
import it.bitify.esercizio.model.Transaction;
import it.bitify.esercizio.dto.PagedResponse;
import it.bitify.esercizio.dto.ApiResponse;
import it.bitify.esercizio.util.AppConstants;


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

   //@PreAuthorize("hasAuthority('transaction_list_all')")
   @GetMapping("/all")
   public ResponseEntity<Object> getAllTransactions() {
      return new ResponseEntity<>(transactionService.getAll(), HttpStatus.OK);
   }
   
   //@PreAuthorize("hasAuthority('transaction_list_paged')")
   @GetMapping
   public PagedResponse<Transaction> getTransactions( @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                               				  @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
														      @RequestParam(value = "sortdir", defaultValue = "1") int sortdir,
														      @RequestParam(value = "sortfield", defaultValue = "transactionId") String sortfield,
   															  @RequestParam(value = "searchstring", defaultValue = "") String searchstring) {
   																  
       return transactionService.getAllPaged(page, size, sortdir,sortfield,searchstring);
   }
   
   //@PreAuthorize("hasAuthority('transaction_by_id')")
   @GetMapping("/{id}")
   public Transaction getTransactionById(@PathVariable Long id) {
       return transactionService.findById(id).get();
   }
   
   //@PreAuthorize("hasAuthority('transaction_update')")
   @PutMapping
   public ResponseEntity<Object> 
      updateTransaction(@RequestBody Transaction transaction) {
      
	   transactionService.updateTransaction(transaction);
      return ResponseEntity.ok(new ApiResponse(true, "Transaction is updated successsfully"));
   }
   
   //@PreAuthorize("hasAuthority('transaction_delete')")
   @DeleteMapping("/{id}")
   public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
	   transactionService.deleteTransaction(id);
      return ResponseEntity.ok(new ApiResponse(true, "Transaction is deleted successsfully"));
   }
   
   //@PreAuthorize("hasAuthority('transaction_delete_all')")
   @PutMapping("/deleteAll")
   public ResponseEntity<Object> 
      deleteTransactions(@RequestBody Transaction[] transactions) {
      
	   transactionService.deleteTransactions(transactions);
      return ResponseEntity.ok(new ApiResponse(true, "Transactions are deleted successsfully"));
   }
   
   //@PreAuthorize("hasAuthority('transaction_create')")
   @PostMapping
   public ResponseEntity<Object> createTransaction(@RequestBody Transaction transaction) {
	   transactionService.createTransaction(transaction);
      return ResponseEntity.ok(new ApiResponse(true, "Transaction is created successsfully"));
   }
   	
        //@PreAuthorize("hasAuthority('transaction_report_pdf')")
	@GetMapping("/report/pdf")
	public void generateReportPdf(HttpServletResponse response) {
		transactionService.generateReportPdf(response);
	}
        //@PreAuthorize("hasAuthority('transaction_report_xls')")
	@GetMapping("/report/xls")
	public void generateReportXls(HttpServletResponse response) {
		transactionService.generateReportXls(response);
	}
        //@PreAuthorize("hasAuthority('transaction_report_csv')")
	@GetMapping("/report/csv")
	public void generateReportCsv(HttpServletResponse response) {
		transactionService.generateReportCsv(response);
	}
   
}
