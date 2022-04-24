/**************************************************************************
*
* Copyright 2022 (C) Bitify s.r.l.
*
* Created on  : 2022-04-24
* Author      : A. Di Raffaele
* Project Name: esercizio 
* Package     : esercizio
* File Name   : EsercizioApplication.java
*
*-----------------------------------------------------------------------------
* Revision History (Release )
*-----------------------------------------------------------------------------
* VERSION     DESCRIPTION OF CHANGE
*-----------------------------------------------------------------------------
** --/1.0  |  Initial Create.
**---------|------------------------------------------------------------------
***************************************************************************/
package it.bitify.esercizio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.util.Date;
import java.util.TimeZone;
import javax.annotation.PostConstruct;

/*******************************************************************************************
 * Created by A. Di Raffaele
 * The main Application EsercizioApplication
 ******************************************************************************************/
@SpringBootApplication
public class EsercizioApplication {
	
	Logger logger = LoggerFactory.getLogger(EsercizioApplication.class);
	
	@Autowired
	public Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(EsercizioApplication.class, args);
	}
	
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Rome"));
		logger.info("Spring boot application running in UTC timezone :"+new Date());
		for (int i = 0; i < this.environment.getActiveProfiles().length; i++) {
			logger.info(i+" ACTIVE PROFILE: *** "+this.environment.getActiveProfiles()[i]+" ***");
		}
	}
	

}
