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
   
   JasperReport jasperReport;
   
   public AccountServiceImpl() {
		try {
			// Compile the Jasper report from .jrxml to .japser
			InputStream accountStream= getClass().getResourceAsStream("/report/jrxml/AccountReport.jrxml");
			jasperReport= JasperCompileManager.compileReport(accountStream);
			new File("report").mkdirs();
			JRSaver.saveObject(jasperReport, "report/accountReport.jasper");
			logger.debug("accountReport.jasper saved");

		} catch (JRException e) {
			e.printStackTrace();
			logger.error("Error--> check the console log");
		}
   }
	
   @Override
   public void createAccount(Account account) {
	   accountRepo.save(account);
	   logger.debug("Account created");
   }
   @Override
   public void updateAccount(Account account) {
	   for (int i = 0; i < account.getTransactions().size(); i++) {
		   account.getTransactions().get(i).setAccount(account);
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
   
   @Override
   public void generateReportPdf(HttpServletResponse response) {
	   logger.debug("Account generateReportPdf");
		try {

			List<Account> accounts = accountRepo.findAll();
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(accounts);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("title", "Account");
			parameters.put("title_sub", "Relazioni");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
					jrBeanCollectionDataSource);
			JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "inline; filename=Account.pdf;");
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
			List<Account> accounts = accountRepo.findAll();
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(accounts);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("title", "Account");
			parameters.put("title_sub", "Relazioni");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
					jrBeanCollectionDataSource);
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
			reportConfig.setSheetNames(new String[] { "Account" });
			exporter.setConfiguration(reportConfig);
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
			response.setHeader("Content-Disposition", "attachment;filename=Account.xlsx");
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
			List<Account> accounts = accountRepo.findAll();
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(accounts);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("title", "Account");
			parameters.put("title_sub", "Relazioni");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
					jrBeanCollectionDataSource);
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleWriterExporterOutput(response.getOutputStream()));
			response.setHeader("Content-Disposition", "attachment;filename=Account.csv");
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
	public Optional<Account> findByAccountId(Long accountId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
	
	
}
