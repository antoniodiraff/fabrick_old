/**************************************************************************
*
* Copyright 2022 (C) Bitify s.r.l.
*
* Created on  : 2022-04-24
* Author      : A. Di Raffaele
* Project Name: esercizio 
* Package     : controller
* File Name   : AuditRegisterController.java
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

import it.bitify.esercizio.service.AuditRegisterService;
import it.bitify.esercizio.model.AuditRegister;
import it.bitify.esercizio.dto.PagedResponse;
import it.bitify.esercizio.dto.ApiResponse;
import it.bitify.esercizio.util.AppConstants;
import springfox.documentation.annotations.ApiIgnore;


/*******************************************************************************************
 * Created by A. Di Raffaele 
 * The AuditRegister
 ******************************************************************************************/
@RestController
@RequestMapping("/api/auditregister")
public class AuditRegisterController {
	
   Logger logger = LoggerFactory.getLogger(AuditRegisterController.class);
	
   @Autowired
   AuditRegisterService auditregisterService;

   @GetMapping("/all")
   public ResponseEntity<Object> getAllAuditRegisters() {
      return new ResponseEntity<>(auditregisterService.getAll(), HttpStatus.OK);
   }
   
   @GetMapping
   public PagedResponse<AuditRegister> getAuditRegisters( @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                               				  @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
														      @RequestParam(value = "sortdir", defaultValue = "1") int sortdir,
														      @RequestParam(value = "sortfield", defaultValue = "auditId") String sortfield,
   															  @RequestParam(value = "searchstring", defaultValue = "") String searchstring) {
   																  
       return auditregisterService.getAllPaged(page, size, sortdir,sortfield,searchstring);
   }
   
   @GetMapping("/{id}")
   public AuditRegister getAuditRegisterById(@PathVariable Long id) {
       return auditregisterService.findById(id).get();
   }
   
   @PutMapping
   public ResponseEntity<Object> 
      updateAuditRegister(@RequestBody AuditRegister auditregister) {
      
	   auditregisterService.updateAuditRegister(auditregister);
      return ResponseEntity.ok(new ApiResponse(true, "AuditRegister is updated successsfully"));
   }
   
   @DeleteMapping("/{id}")
   public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
	   auditregisterService.deleteAuditRegister(id);
      return ResponseEntity.ok(new ApiResponse(true, "AuditRegister is deleted successsfully"));
   }
   
   @PutMapping("/deleteAll")
   public ResponseEntity<Object> 
      deleteAuditRegisters(@RequestBody AuditRegister[] auditregisters) {
      
	   auditregisterService.deleteAuditRegisters(auditregisters);
      return ResponseEntity.ok(new ApiResponse(true, "AuditRegisters are deleted successsfully"));
   }
   
   @PostMapping
   public ResponseEntity<Object> createAuditRegister(@RequestBody AuditRegister auditregister) {
	   auditregisterService.createAuditRegister(auditregister);
      return ResponseEntity.ok(new ApiResponse(true, "AuditRegister is created successsfully"));
   }
   	
   @ApiIgnore
	@GetMapping("/report/pdf")
	public void generateReportPdf(HttpServletResponse response) {
		auditregisterService.generateReportPdf(response);
	}
   @ApiIgnore
	@GetMapping("/report/xls")
	public void generateReportXls(HttpServletResponse response) {
		auditregisterService.generateReportXls(response);
	}
   @ApiIgnore
	@GetMapping("/report/csv")
	public void generateReportCsv(HttpServletResponse response) {
		auditregisterService.generateReportCsv(response);
	}
   
}
