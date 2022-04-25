/**************************************************************************
*
* Copyright 2022 (C) Bitify s.r.l.
*
* Created on  : 2022-04-24
* Author      : A. Di Raffaele
* Project Name: esercizio 
* Package     : controller
* File Name   : TypeController.java
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

import io.swagger.v3.oas.annotations.Hidden;

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

import it.bitify.esercizio.service.TypeService;
import it.bitify.esercizio.model.Type;
import it.bitify.esercizio.dto.PagedResponse;
import it.bitify.esercizio.dto.ApiResponse;
import it.bitify.esercizio.util.AppConstants;
import springfox.documentation.annotations.ApiIgnore;


/*******************************************************************************************
 * Created by A. Di Raffaele 
 * The Type
 ******************************************************************************************/
@ApiIgnore
@RestController
@RequestMapping("/api/type")
public class TypeController {
	
   Logger logger = LoggerFactory.getLogger(TypeController.class);
	
   @Autowired
   TypeService typeService;

   @GetMapping("/all")
   public ResponseEntity<Object> getAllTypes() {
      return new ResponseEntity<>(typeService.getAll(), HttpStatus.OK);
   }
   
   @GetMapping
   public PagedResponse<Type> getTypes( @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                               				  @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
														      @RequestParam(value = "sortdir", defaultValue = "1") int sortdir,
														      @RequestParam(value = "sortfield", defaultValue = "enumeration") String sortfield,
   															  @RequestParam(value = "searchstring", defaultValue = "") String searchstring) {
   																  
       return typeService.getAllPaged(page, size, sortdir,sortfield,searchstring);
   }
   
   @GetMapping("/{id}")
   public Type getTypeById(@PathVariable String id) {
       return typeService.findById(id).get();
   }
   
   @PutMapping
   public ResponseEntity<Object> 
      updateType(@RequestBody Type type) {
      
	   typeService.updateType(type);
      return ResponseEntity.ok(new ApiResponse(true, "Type is updated successsfully"));
   }
   
   @DeleteMapping("/{id}")
   public ResponseEntity<Object> delete(@PathVariable("id") String id) {
	   typeService.deleteType(id);
      return ResponseEntity.ok(new ApiResponse(true, "Type is deleted successsfully"));
   }
   
   @PutMapping("/deleteAll")
   public ResponseEntity<Object> 
      deleteTypes(@RequestBody Type[] types) {
      
	   typeService.deleteTypes(types);
      return ResponseEntity.ok(new ApiResponse(true, "Types are deleted successsfully"));
   }
   
   @PostMapping
   public ResponseEntity<Object> createType(@RequestBody Type type) {
	   typeService.createType(type);
      return ResponseEntity.ok(new ApiResponse(true, "Type is created successsfully"));
   }
   	
   @ApiIgnore
   @GetMapping("/report/pdf")
	public void generateReportPdf(HttpServletResponse response) {
		typeService.generateReportPdf(response);
	}
	@ApiIgnore
	@GetMapping("/report/xls")
	public void generateReportXls(HttpServletResponse response) {
		typeService.generateReportXls(response);
	}
	@ApiIgnore
	@GetMapping("/report/csv")
	public void generateReportCsv(HttpServletResponse response) {
		typeService.generateReportCsv(response);
	}
   
}
