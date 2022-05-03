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
public class AccountControllerTest {
 
  @Autowired
  private MockMvc mvc;
  
  @Test
  public void getAccount() throws Exception 
  {
	  String testAccountId="14537780";
	  mvc.perform( MockMvcRequestBuilders 
        .get("/api/account/sandbox/"+testAccountId)
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
	    .andExpect(MockMvcResultMatchers.jsonPath("$.payload").exists())
	    .andExpect(MockMvcResultMatchers.jsonPath("$.payload.accountId").isNotEmpty());
  }
  
  @Test
  public void create() throws Exception 
  {
	String element="{\"accountId\":\"1\"}";
 
    mvc.perform( MockMvcRequestBuilders
        .post("/api/account")
        .content(element)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }
  
 
  
  /*@WithMockUser(username = "admin", authorities = { "account_list_paged" })*/
  @Test
  public void getAllPaginated() throws Exception 
  {
    mvc.perform( MockMvcRequestBuilders
        .get("/api/account")
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
        //.andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
        //.andExpect(MockMvcResultMatchers.jsonPath("$.content[*].id").isNotEmpty());
  }
  
  /*@WithMockUser(username = "admin", authorities = { "account_list_all" })*/
  @Test
  public void getAll() throws Exception 
  {
    mvc.perform( MockMvcRequestBuilders
        .get("/api/account/all")
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
        //.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
        //.andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty());
  }
  
  /*@WithMockUser(username = "admin", authorities = { "account_delete" })*/
  @Test
  public void delete() throws Exception 
  {
    mvc.perform( MockMvcRequestBuilders
        .delete("/api/account/11")
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }
 
}
