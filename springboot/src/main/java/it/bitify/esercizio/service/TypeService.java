/**************************************************************************
*
* Copyright 2022 (C) Bitify s.r.l.
*
* Created on  : 2022-04-24
* Author      : A. Di Raffaele
* Project Name: esercizio 
* Package     : service
* File Name   : TypeService.java
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
import it.bitify.esercizio.model.Type;
import it.bitify.esercizio.dto.PagedResponse;

/**
 * Created by A. Di Raffaele
 * The Type Service
 */
public interface TypeService {
   public abstract void createType(Type type);
   public abstract void updateType(Type type);
   public abstract void deleteType(String  id);
   public abstract void deleteTypes(Type[] types);
   public abstract Optional<Type> findById(String id);
   public abstract Collection<Type> getAll();
   public abstract PagedResponse<Type> getAllPaged(int page, int size, int sortDirection,String sortField,String searchString);
}
