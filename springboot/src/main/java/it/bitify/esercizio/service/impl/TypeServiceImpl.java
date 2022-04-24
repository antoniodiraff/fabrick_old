/**************************************************************************
*
* Copyright 2022 (C) Bitify s.r.l.
*
* Created on  : 2022-04-24
* Author      : A. Di Raffaele
* Project Name: esercizio 
* Package     : service.impl
* File Name   : TypeServiceImpl.java
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
import it.bitify.esercizio.model.Type;
import it.bitify.esercizio.service.TypeService;
import it.bitify.esercizio.repository.TypeRepository;

/**
 * Created by A. Di Raffaele 
 * The Type Service Impl
 */
@Service
public class TypeServiceImpl implements TypeService {
	
   Logger logger = LoggerFactory.getLogger(TypeServiceImpl.class);
   
   @Autowired
   private TypeRepository typeRepo;
   
   JasperReport jasperReport;
   
   public TypeServiceImpl() {
		try {
			// Compile the Jasper report from .jrxml to .japser
			InputStream typeStream= getClass().getResourceAsStream("/report/jrxml/TypeReport.jrxml");
			jasperReport= JasperCompileManager.compileReport(typeStream);
			new File("report").mkdirs();
			JRSaver.saveObject(jasperReport, "report/typeReport.jasper");
			logger.debug("typeReport.jasper saved");

		} catch (JRException e) {
			e.printStackTrace();
			logger.error("Error--> check the console log");
		}
   }
	
   @Override
   public void createType(Type type) {
	   typeRepo.save(type);
	   logger.debug("Type created");
   }
   @Override
   public void updateType(Type type) {
	   
	   typeRepo.save(type);
	   logger.debug("Type updated");
   }
   @Override
   public void deleteType(String  id) {
	   typeRepo.delete(typeRepo.getById(id));
	   logger.debug("Type deleted");
   }
   @Override
	public void deleteTypes(Type[] types) {
		for (int i = 0; i < types.length; i++) {
			deleteType(types[i].getEnumeration());
			logger.debug("Type deleted");
		}
	}
   @Override
   public Optional<Type> findById(String id) {
	   logger.debug("Type findById execution ID:"+id);
   	   return typeRepo.findById(id);
   }
   @Override
   public Collection<Type> getAll() {
	   logger.debug("Type getAll");
       return typeRepo.findAll();
   }
   @Override
   public PagedResponse<Type> getAllPaged(int page, int size, int sortDirection,String sortField,String searchString) {
	   logger.debug("Type getAllPaged");
       PageUtils.validatePageNumberAndSize(page, size);

       Direction dir = Sort.Direction.DESC;
       if(sortDirection==-1) {
    	   dir= Sort.Direction.ASC;
       }
       Pageable pageable = PageRequest.of(page, size, dir, sortField);
       Page<Type> types;
		if(searchString!=null && !searchString.isEmpty()) {
			//TODO global search
			types = typeRepo.findAll(pageable);
		}
		else {
			types = typeRepo.findAll(pageable);
		}

       if(types.getNumberOfElements() == 0) {
           return new PagedResponse<>(Collections.emptyList(), types.getNumber(),
        		   types.getSize(), types.getTotalElements(), types.getTotalPages(), types.isLast());
       }

       return new PagedResponse<>(types.toList(), types.getNumber(),
    		   types.getSize(), types.getTotalElements(), types.getTotalPages(), types.isLast());
   }
   
   @Override
   public void generateReportPdf(HttpServletResponse response) {
	   logger.debug("Type generateReportPdf");
		try {

			List<Type> types = typeRepo.findAll();
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(types);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("title", "Type");
			parameters.put("title_sub", "Relazioni");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
					jrBeanCollectionDataSource);
			JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "inline; filename=Type.pdf;");
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
			List<Type> types = typeRepo.findAll();
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(types);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("title", "Type");
			parameters.put("title_sub", "Relazioni");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
					jrBeanCollectionDataSource);
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
			reportConfig.setSheetNames(new String[] { "Type" });
			exporter.setConfiguration(reportConfig);
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
			response.setHeader("Content-Disposition", "attachment;filename=Type.xlsx");
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
			List<Type> types = typeRepo.findAll();
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(types);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("title", "Type");
			parameters.put("title_sub", "Relazioni");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
					jrBeanCollectionDataSource);
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleWriterExporterOutput(response.getOutputStream()));
			response.setHeader("Content-Disposition", "attachment;filename=Type.csv");
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
