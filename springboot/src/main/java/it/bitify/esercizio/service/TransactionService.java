/**************************************************************************
*
* Copyright 2022 (C) Bitify s.r.l.
*
* Created on  : 2022-04-24
* Author      : A. Di Raffaele
* Project Name: esercizio 
* Package     : service
* File Name   : TransactionService.java
*
*-----------------------------------------------------------------------------
* Revision History (Release )
*-----------------------------------------------------------------------------
* VERSION     DESCRIPTION OF CHANGE
*-----------------------------------------------------------------------------
** --/1.0  |  Initial Create.
**---------|------------------------------------------------------------------
***************************************************************************/
package it.bitify.esercizio.service;

import java.util.Collection;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import it.bitify.esercizio.model.Transaction;
import it.bitify.esercizio.dto.PagedResponse;

/**
 * Created by A. Di Raffaele
 * The Transaction Service
 */
public interface TransactionService {
   public abstract void createTransaction(Transaction transaction);
   public abstract void updateTransaction(Transaction transaction);
   public abstract void deleteTransaction(Long  id);
   public abstract void deleteTransactions(Transaction[] transactions);
   public abstract Optional<Transaction> findById(Long id);
   public abstract Collection<Transaction> getAll();
   public abstract PagedResponse<Transaction> getAllPaged(int page, int size, int sortDirection,String sortField,String searchString);
   void createTransactions(Transaction[] transactions);
}
