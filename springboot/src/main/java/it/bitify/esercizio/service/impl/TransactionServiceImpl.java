/**************************************************************************
*
* Copyright 2022 (C) Bitify s.r.l.
*
* Created on  : 2022-04-24
* Author      : A. Di Raffaele
* Project Name: esercizio 
* Package     : service.impl
* File Name   : TransactionServiceImpl.java
*
*-----------------------------------------------------------------------------
* Revision History (Release )
*-----------------------------------------------------------------------------
* VERSION     DESCRIPTION OF CHANGE
*-----------------------------------------------------------------------------
** --/1.0  |  Initial Create.
**---------|------------------------------------------------------------------
***************************************************************************/
package it.bitify.esercizio.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import it.bitify.esercizio.dto.PagedResponse;
import it.bitify.esercizio.util.PageUtils;
import it.bitify.esercizio.model.Transaction;
import it.bitify.esercizio.service.TransactionService;
import it.bitify.esercizio.repository.TransactionRepository;

/**
 * Created by A. Di Raffaele 
 * The Transaction Service Impl
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
	
   Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
   
   @Autowired
   private TransactionRepository transactionRepo;
   
	
   @Override
   public void createTransaction(Transaction transaction) {
	   transactionRepo.save(transaction);
	   logger.debug("Transaction created");
   }
   
   @Override
   public void createTransactions(Transaction[] transactions) {
		for (int i = 0; i < transactions.length; i++) {
			createTransaction(transactions[i]);
		}
   }
   
   @Override
   public void updateTransaction(Transaction transaction) {
	   
	   transactionRepo.save(transaction);
	   logger.debug("Transaction updated");
   }
   @Override
   public void deleteTransaction(Long  id) {
	   transactionRepo.delete(transactionRepo.getById(id));
	   logger.debug("Transaction deleted");
   }
   @Override
	public void deleteTransactions(Transaction[] transactions) {
		for (int i = 0; i < transactions.length; i++) {
			deleteTransaction(transactions[i].getTransactionId());
			logger.debug("Transaction deleted");
		}
	}
   @Override
   public Optional<Transaction> findById(Long id) {
	   logger.debug("Transaction findById execution ID:"+id);
   	   return transactionRepo.findById(id);
   }
   @Override
   public Collection<Transaction> getAll() {
	   logger.debug("Transaction getAll");
       return transactionRepo.findAll();
   }
   @Override
   public PagedResponse<Transaction> getAllPaged(int page, int size, int sortDirection,String sortField,String searchString) {
	   logger.debug("Transaction getAllPaged");
       PageUtils.validatePageNumberAndSize(page, size);

       Direction dir = Sort.Direction.DESC;
       if(sortDirection==-1) {
    	   dir= Sort.Direction.ASC;
       }
       Pageable pageable = PageRequest.of(page, size, dir, sortField);
       Page<Transaction> transactions;
		if(searchString!=null && !searchString.isEmpty()) {
			//TODO global search
			transactions = transactionRepo.findAll(pageable);
		}
		else {
			transactions = transactionRepo.findAll(pageable);
		}

       if(transactions.getNumberOfElements() == 0) {
           return new PagedResponse<>(Collections.emptyList(), transactions.getNumber(),
        		   transactions.getSize(), transactions.getTotalElements(), transactions.getTotalPages(), transactions.isLast());
       }

       return new PagedResponse<>(transactions.toList(), transactions.getNumber(),
    		   transactions.getSize(), transactions.getTotalElements(), transactions.getTotalPages(), transactions.isLast());
   }
   
}
