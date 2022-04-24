/**************************************************************************
*
* Copyright 2022 (C) Bitify s.r.l.
*
* Created on  : 2022-04-24
* Author      : A. Di Raffaele
* Project Name: esercizio 
* Package     : service.impl
* File Name   : TransactionServiceImpl.java
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
import it.bitify.esercizio.model.Transaction;
import it.bitify.esercizio.service.TransactionService;
import it.bitify.esercizio.repository.TransactionRepository;

/**
 * Created by A. Di Raffaele 
 * The Transaction Service Impl
 */
@Service
public class TransactionServiceImpl implements TransactionService {
	
   Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
   
   @Autowired
   private TransactionRepository transactionRepo;
   
   JasperReport jasperReport;
   
   public TransactionServiceImpl() {
		try {
			// Compile the Jasper report from .jrxml to .japser
			InputStream transactionStream= getClass().getResourceAsStream("/report/jrxml/TransactionReport.jrxml");
			jasperReport= JasperCompileManager.compileReport(transactionStream);
			new File("report").mkdirs();
			JRSaver.saveObject(jasperReport, "report/transactionReport.jasper");
			logger.debug("transactionReport.jasper saved");

		} catch (JRException e) {
			e.printStackTrace();
			logger.error("Error--> check the console log");
		}
   }
	
   @Override
   public void createTransaction(Transaction transaction) {
	   transactionRepo.save(transaction);
	   logger.debug("Transaction created");
   }
   @Override
   public void updateTransaction(Transaction transaction) {
	   
	   transactionRepo.save(transaction);
	   logger.debug("Transaction updated");
   }
   @Override
   public void deleteTransaction(Long  id) {
	   transactionRepo.delete(transactionRepo.getById(id));
	   logger.debug("Transaction deleted");
   }
   @Override
	public void deleteTransactions(Transaction[] transactions) {
		for (int i = 0; i < transactions.length; i++) {
			deleteTransaction(transactions[i].getTransactionId());
			logger.debug("Transaction deleted");
		}
	}
   @Override
   public Optional<Transaction> findById(Long id) {
	   logger.debug("Transaction findById execution ID:"+id);
   	   return transactionRepo.findById(id);
   }
   @Override
   public Collection<Transaction> getAll() {
	   logger.debug("Transaction getAll");
       return transactionRepo.findAll();
   }
   @Override
   public PagedResponse<Transaction> getAllPaged(int page, int size, int sortDirection,String sortField,String searchString) {
	   logger.debug("Transaction getAllPaged");
       PageUtils.validatePageNumberAndSize(page, size);

       Direction dir = Sort.Direction.DESC;
       if(sortDirection==-1) {
    	   dir= Sort.Direction.ASC;
       }
       Pageable pageable = PageRequest.of(page, size, dir, sortField);
       Page<Transaction> transactions;
		if(searchString!=null && !searchString.isEmpty()) {
			//TODO global search
			transactions = transactionRepo.findAll(pageable);
		}
		else {
			transactions = transactionRepo.findAll(pageable);
		}

       if(transactions.getNumberOfElements() == 0) {
           return new PagedResponse<>(Collections.emptyList(), transactions.getNumber(),
        		   transactions.getSize(), transactions.getTotalElements(), transactions.getTotalPages(), transactions.isLast());
       }

       return new PagedResponse<>(transactions.toList(), transactions.getNumber(),
    		   transactions.getSize(), transactions.getTotalElements(), transactions.getTotalPages(), transactions.isLast());
   }
   
   @Override
   public void generateReportPdf(HttpServletResponse response) {
	   logger.debug("Transaction generateReportPdf");
		try {

			List<Transaction> transactions = transactionRepo.findAll();
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(transactions);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("title", "Transaction");
			parameters.put("title_sub", "Relazioni");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
					jrBeanCollectionDataSource);
			JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "inline; filename=Transaction.pdf;");
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
			List<Transaction> transactions = transactionRepo.findAll();
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(transactions);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("title", "Transaction");
			parameters.put("title_sub", "Relazioni");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
					jrBeanCollectionDataSource);
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
			reportConfig.setSheetNames(new String[] { "Transaction" });
			exporter.setConfiguration(reportConfig);
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
			response.setHeader("Content-Disposition", "attachment;filename=Transaction.xlsx");
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
			List<Transaction> transactions = transactionRepo.findAll();
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(transactions);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("title", "Transaction");
			parameters.put("title_sub", "Relazioni");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
					jrBeanCollectionDataSource);
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleWriterExporterOutput(response.getOutputStream()));
			response.setHeader("Content-Disposition", "attachment;filename=Transaction.csv");
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
