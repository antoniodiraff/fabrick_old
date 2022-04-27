/**************************************************************************
*
* Copyright 2022 (C) Bitify s.r.l.
*
* Created on  : 2022-04-24
* Author      : A. Di Raffaele
* Project Name: esercizio 
* Package     : service
* File Name   : AuditRegisterService.java
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
import it.bitify.esercizio.model.AuditRegister;
import it.bitify.esercizio.dto.PagedResponse;

/**
 * Created by A. Di Raffaele
 * The AuditRegister Service
 */
public interface AuditRegisterService {
   public abstract void createAuditRegister(AuditRegister auditregister);
   public abstract void updateAuditRegister(AuditRegister auditregister);
   public abstract void deleteAuditRegister(Long  id);
   public abstract void deleteAuditRegisters(AuditRegister[] auditregisters);
   public abstract Optional<AuditRegister> findById(Long id);
   public abstract Collection<AuditRegister> getAll();
   public abstract PagedResponse<AuditRegister> getAllPaged(int page, int size, int sortDirection,String sortField,String searchString);
}
