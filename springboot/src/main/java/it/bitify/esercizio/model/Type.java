/**************************************************************************
*
* Copyright 2022 (C) Bitify s.r.l.
*
* Created on  : 2022-04-24
* Author      : A. Di Raffaele
* Project Name: esercizio 
* Package     : model
* File Name   : Type.java
*
*-----------------------------------------------------------------------------
* Revision History (Release )
*-----------------------------------------------------------------------------
* VERSION     DESCRIPTION OF CHANGE
*-----------------------------------------------------------------------------
** --/1.0  |  Initial Create.
**---------|------------------------------------------------------------------
***************************************************************************/
package it.bitify.esercizio.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;

//optional
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;

import it.bitify.esercizio.model.audit.UserDateAudit;
//import it.bitify.esercizio.security.model.SecUser;


/**
 * Created by A. Di Raffaele 
 * Type: Entità
 */
@Entity
@Table(name = "TYPE")
public class Type{
	    
/** enumeration: add comment...  */
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="enumeration")
private String enumeration;

/** value: add comment...  */
@Column(name="value")
private String value;

/** Transaction Relation:  */
@OneToMany(mappedBy="type")
@JsonIgnore
private List<Transaction> transactions;


public String getEnumeration(){
	return enumeration;
}

public void setEnumeration(String enumeration){
	this.enumeration=enumeration;
}


public String getValue(){
	return value;
}

public void setValue(String value){
	this.value=value;
}


public List<Transaction> getTransactions(){
	return transactions;
}

public void setTransactions(List<Transaction> transactions){
	this.transactions=transactions;
}


}
