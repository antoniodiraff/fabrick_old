/**************************************************************************
*
* Copyright 2022 (C) Bitify s.r.l.
*
* Created on  : 2022-04-24
* Author      : A. Di Raffaele
* Project Name: esercizio 
* Package     : service
* File Name   : AccountService.java
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
import it.bitify.esercizio.model.Account;
import it.bitify.esercizio.dto.PagedResponse;

/**
 * Created by A. Di Raffaele
 * The Account Service
 */
public interface AccountService {
   public abstract void createAccount(Account account);
   public abstract void updateAccount(Account account);
   public abstract void deleteAccount(Long  id);
   public abstract void deleteAccounts(Account[] accounts);
   public abstract Optional<Account> findById(Long id);
   public abstract Collection<Account> getAll();
   public abstract PagedResponse<Account> getAllPaged(int page, int size, int sortDirection,String sortField,String searchString);
}
