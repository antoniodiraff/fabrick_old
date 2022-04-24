/**************************************************************************
*
* Copyright 2022 (C) Bitify s.r.l.
*
* Created on  : 2022-04-24
* Author      : A. Di Raffaele
* Project Name: esercizio 
* Package     : test.controller
* File Name   : AuditRegisterControllerTest.java
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
public class AuditRegisterControllerTest {
 
  @Autowired
  private MockMvc mvc;
  
  /*@WithMockUser(username = "admin", authorities = { "auditregister_create" })*/
  @Test
  public void create() throws Exception 
  {
    String element="{}";
    
	  
    mvc.perform( MockMvcRequestBuilders
        .post("/api/auditregister")
        .content(element)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }
  
  /*@WithMockUser(username = "admin", authorities = { "auditregister_update" })*/
  @Test
  public void update() throws Exception 
  {
    String element="{\"auditId\":\"1\"}";
    
	  
    mvc.perform( MockMvcRequestBuilders
        .put("/api/auditregister")
        .content(element)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }
  
  /*@WithMockUser(username = "admin", authorities = { "auditregister_list_paged" })*/
  @Test
  public void getAllPaginated() throws Exception 
  {
    mvc.perform( MockMvcRequestBuilders
        .get("/api/auditregister")
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
        //.andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
        //.andExpect(MockMvcResultMatchers.jsonPath("$.content[*].id").isNotEmpty());
  }
  
  /*@WithMockUser(username = "admin", authorities = { "auditregister_list_all" })*/
  @Test
  public void getAll() throws Exception 
  {
    mvc.perform( MockMvcRequestBuilders
        .get("/api/auditregister/all")
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
        //.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
        //.andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty());
  }
  
  /*@WithMockUser(username = "admin", authorities = { "auditregister_delete" })*/
  @Test
  public void delete() throws Exception 
  {
    mvc.perform( MockMvcRequestBuilders
        .delete("/api/auditregister/11")
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }
 
}
