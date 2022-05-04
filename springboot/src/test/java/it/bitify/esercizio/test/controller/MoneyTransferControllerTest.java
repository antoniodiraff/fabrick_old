/**************************************************************************
*
* Copyright 2022 (C) Bitify s.r.l.
*
* Created on  : 2022-04-24
* Author      : A. Di Raffaele
* Project Name: esercizio 
* Package     : test.controller
* File Name   : AccountControllerTest.java
*
*-----------------------------------------------------------------------------
* Revision History (Release )
*-----------------------------------------------------------------------------
* VERSION     DESCRIPTION OF CHANGE
*-----------------------------------------------------------------------------
** --/1.0  |  Initial Create.
**---------|------------------------------------------------------------------
***************************************************************************/
package it.bitify.esercizio.test.controller;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
/*
import org.springframework.security.test.context.support.WithMockUser;
*/


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MoneyTransferControllerTest {
 
  @Autowired
  private MockMvc mvc;
  
 
  
  @Test
  public void create() throws Exception 
  {
	String element="{\"creditor\":{\r\n"
			+ "   \"name\":\"John Doe\",\r\n"
			+ "   \"account\":{\r\n"
			+ "      \"accountCode\":\"IT23A0336844430152923804660\",\r\n"
			+ "      \"bicCode\":\"SELBIT2BXXX\"\r\n"
			+ "   },\r\n"
			+ "   \"address\":{\r\n"
			+ "      \"address\":null,\r\n"
			+ "      \"city\":null,\r\n"
			+ "      \"countryCode\":null\r\n"
			+ "   }\r\n"
			+ "},\r\n"
			+ "\"executionDate\":\"2019-04-01\",\r\n"
			+ "\"uri\":\"REMITTANCE_INFORMATION\",\r\n"
			+ "\"description\":\"Payment invoice 75/2017\",\r\n"
			+ "\"amount\":800,\r\n"
			+ "\"currency\":\"EUR\",\r\n"
			+ "\"feeType\":\"SHA\",\r\n"
			+ "\"feeAccountId\":\"14537780\",\r\n"
			+ "\"taxRelief\":{\r\n"
			+ "   \"taxReliefId\":\"L449\",\r\n"
			+ "   \"creditorFiscalCode\":\"56258745832\",\r\n"
			+ "   \"beneficiaryType\":\"NATURAL_PERSON\",\r\n"
			+ "   \"naturalPersonBeneficiary\":{\r\n"
			+ "      \"fiscalCode1\":\"MRLFNC81L04A859L\",\r\n"
			+ "      \"fiscalCode2\":null,\r\n"
			+ "      \"fiscalCode3\":null,\r\n"
			+ "      \"fiscalCode4\":null,\r\n"
			+ "      \"fiscalCode5\":null\r\n"
			+ "   },\r\n"
			+ "   \"legalPersonBeneficiary\":{\r\n"
			+ "      \"fiscalCode\":null,\r\n"
			+ "      \"legalRepresentativeFiscalCode\":null\r\n"
			+ "   },\r\n"
			+ "   \"condoUpgrade\":false\r\n"
			+ "},\r\n"
			+ "\"instant\":false,\r\n"
			+ "\"urgent\":false\r\n"
			+ "}";
	
	String testAccountId="14537780";
 
    mvc.perform( MockMvcRequestBuilders
        .post("/api/moneytransfer/sandbox/"+testAccountId)
        .content(element)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.payload").exists());
  }
  
 
}
