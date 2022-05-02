/**************************************************************************
*
* Copyright 2022 (C) Bitify s.r.l.
*
* Created on  : 2022-04-24
* Author      : A. Di Raffaele
* Project Name: esercizio 
* Package     : service.impl
* File Name   : AccountServiceImpl.java
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

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

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
import it.bitify.esercizio.model.Account;
import it.bitify.esercizio.service.AccountService;
import it.bitify.esercizio.repository.AccountRepository;

/**
 * Created by A. Di Raffaele 
 * The Account Service Impl
 */
@Service
public class AccountServiceImpl implements AccountService {
	
   Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
   
   @Autowired
   private AccountRepository accountRepo;
   
	
   @Override
   public void createAccount(Account account) {
	   accountRepo.save(account);
	   logger.debug("Account created");
   }
   @Override
   public void updateAccount(Account account) {
	   if(account.getTransactions()!=null) {
		   for (int i = 0; i < account.getTransactions().size(); i++) {
			   account.getTransactions().get(i).setAccount(account);
		   }
	   }
	   accountRepo.save(account);
	   logger.debug("Account updated");
   }
   @Override
   public void deleteAccount(Long  id) {
	   accountRepo.delete(accountRepo.getById(id));
	   logger.debug("Account deleted");
   }
   @Override
	public void deleteAccounts(Account[] accounts) {
		for (int i = 0; i < accounts.length; i++) {
			deleteAccount(accounts[i].getAccountId());
			logger.debug("Account deleted");
		}
	}
   @Override
   public Optional<Account> findById(Long id) {
	   logger.debug("Account findById execution ID:"+id);
   	   return accountRepo.findById(id);
   }
   @Override
   public Collection<Account> getAll() {
	   logger.debug("Account getAll");
       return accountRepo.findAll();
   }
   @Override
   public PagedResponse<Account> getAllPaged(int page, int size, int sortDirection,String sortField,String searchString) {
	   logger.debug("Account getAllPaged");
       PageUtils.validatePageNumberAndSize(page, size);

       Direction dir = Sort.Direction.DESC;
       if(sortDirection==-1) {
    	   dir= Sort.Direction.ASC;
       }
       Pageable pageable = PageRequest.of(page, size, dir, sortField);
       Page<Account> accounts;
		if(searchString!=null && !searchString.isEmpty()) {
			//TODO global search
			accounts = accountRepo.findAll(pageable);
		}
		else {
			accounts = accountRepo.findAll(pageable);
		}

       if(accounts.getNumberOfElements() == 0) {
           return new PagedResponse<>(Collections.emptyList(), accounts.getNumber(),
        		   accounts.getSize(), accounts.getTotalElements(), accounts.getTotalPages(), accounts.isLast());
       }

       return new PagedResponse<>(accounts.toList(), accounts.getNumber(),
    		   accounts.getSize(), accounts.getTotalElements(), accounts.getTotalPages(), accounts.isLast());
   }
   
}
