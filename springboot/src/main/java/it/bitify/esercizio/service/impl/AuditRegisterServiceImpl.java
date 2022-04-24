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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
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
   
   JasperReport jasperReport;
   
   public AuditRegisterServiceImpl() {
		try {
			// Compile the Jasper report from .jrxml to .japser
			InputStream auditregisterStream= getClass().getResourceAsStream("/report/jrxml/AuditRegisterReport.jrxml");
			jasperReport= JasperCompileManager.compileReport(auditregisterStream);
			new File("report").mkdirs();
			JRSaver.saveObject(jasperReport, "report/auditregisterReport.jasper");
			logger.debug("auditregisterReport.jasper saved");

		} catch (JRException e) {
			e.printStackTrace();
			logger.error("Error--> check the console log");
		}
   }
	
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
   
   @Override
   public void generateReportPdf(HttpServletResponse response) {
	   logger.debug("AuditRegister generateReportPdf");
		try {

			List<AuditRegister> auditregisters = auditregisterRepo.findAll();
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(auditregisters);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("title", "AuditRegister");
			parameters.put("title_sub", "Relazioni");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
					jrBeanCollectionDataSource);
			JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "inline; filename=AuditRegister.pdf;");
			logger.debug("Done");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error--> check the console log"); 
		}
	}

	@Override
	public void generateReportXls(HttpServletResponse response) {
		try {
			JRXlsxExporter exporter = new JRXlsxExporter();
			List<AuditRegister> auditregisters = auditregisterRepo.findAll();
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(auditregisters);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("title", "AuditRegister");
			parameters.put("title_sub", "Relazioni");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
					jrBeanCollectionDataSource);
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
			reportConfig.setSheetNames(new String[] { "AuditRegister" });
			exporter.setConfiguration(reportConfig);
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
			response.setHeader("Content-Disposition", "attachment;filename=AuditRegister.xlsx");
	        response.setContentType("application/octet-stream");

			exporter.exportReport();
		} catch (JRException ex) {
			logger.error(ex.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void generateReportCsv(HttpServletResponse response) {
		try {
			JRCsvExporter exporter = new JRCsvExporter();
			List<AuditRegister> auditregisters = auditregisterRepo.findAll();
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(auditregisters);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("title", "AuditRegister");
			parameters.put("title_sub", "Relazioni");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
					jrBeanCollectionDataSource);
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleWriterExporterOutput(response.getOutputStream()));
			response.setHeader("Content-Disposition", "attachment;filename=AuditRegister.csv");
	        response.setContentType("application/octet-stream");
	        exporter.exportReport();
		} catch (JRException ex) {
			logger.error(ex.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
