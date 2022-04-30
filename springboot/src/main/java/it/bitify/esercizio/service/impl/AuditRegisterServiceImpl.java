/**************************************************************************
*
* Copyright 2022 (C) Bitify s.r.l.
*
* Created on  : 2022-04-24
* Author      : A. Di Raffaele
* Project Name: esercizio 
* Package     : service.impl
* File Name   : AuditRegisterServiceImpl.java
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
import it.bitify.esercizio.model.AuditRegister;
import it.bitify.esercizio.service.AuditRegisterService;
import it.bitify.esercizio.repository.AuditRegisterRepository;

/**
 * Created by A. Di Raffaele 
 * The AuditRegister Service Impl
 */
@Service
public class AuditRegisterServiceImpl implements AuditRegisterService {
	
   Logger logger = LoggerFactory.getLogger(AuditRegisterServiceImpl.class);
   
   @Autowired
   private AuditRegisterRepository auditregisterRepo;
   

	
   @Override
   public void createAuditRegister(AuditRegister auditregister) {
	   auditregisterRepo.save(auditregister);
	   logger.debug("AuditRegister created");
   }
   @Override
   public void updateAuditRegister(AuditRegister auditregister) {
	   
	   auditregisterRepo.save(auditregister);
	   logger.debug("AuditRegister updated");
   }
   @Override
   public void deleteAuditRegister(Long  id) {
	   auditregisterRepo.delete(auditregisterRepo.getById(id));
	   logger.debug("AuditRegister deleted");
   }
   @Override
	public void deleteAuditRegisters(AuditRegister[] auditregisters) {
		for (int i = 0; i < auditregisters.length; i++) {
			deleteAuditRegister(auditregisters[i].getAuditId());
			logger.debug("AuditRegister deleted");
		}
	}
   @Override
   public Optional<AuditRegister> findById(Long id) {
	   logger.debug("AuditRegister findById execution ID:"+id);
   	   return auditregisterRepo.findById(id);
   }
   @Override
   public Collection<AuditRegister> getAll() {
	   logger.debug("AuditRegister getAll");
       return auditregisterRepo.findAll();
   }
   @Override
   public PagedResponse<AuditRegister> getAllPaged(int page, int size, int sortDirection,String sortField,String searchString) {
	   logger.debug("AuditRegister getAllPaged");
       PageUtils.validatePageNumberAndSize(page, size);

       Direction dir = Sort.Direction.DESC;
       if(sortDirection==-1) {
    	   dir= Sort.Direction.ASC;
       }
       Pageable pageable = PageRequest.of(page, size, dir, sortField);
       Page<AuditRegister> auditregisters;
		if(searchString!=null && !searchString.isEmpty()) {
			//TODO global search
			auditregisters = auditregisterRepo.findAll(pageable);
		}
		else {
			auditregisters = auditregisterRepo.findAll(pageable);
		}

       if(auditregisters.getNumberOfElements() == 0) {
           return new PagedResponse<>(Collections.emptyList(), auditregisters.getNumber(),
        		   auditregisters.getSize(), auditregisters.getTotalElements(), auditregisters.getTotalPages(), auditregisters.isLast());
       }

       return new PagedResponse<>(auditregisters.toList(), auditregisters.getNumber(),
    		   auditregisters.getSize(), auditregisters.getTotalElements(), auditregisters.getTotalPages(), auditregisters.isLast());
   }
   
}
