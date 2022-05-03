/**************************************************************************
*
* Copyright 2022 (C) Bitify s.r.l.
*
* Created on  : 2022-04-24
* Author      : A. Di Raffaele
* Project Name: esercizio 
* Package     : model
* File Name   : AuditRegister.java
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
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;
import it.bitify.esercizio.model.audit.UserDateAudit;
//import it.bitify.esercizio.security.model.SecUser;


/**
 * Created by A. Di Raffaele 
 * AuditRegister: Entità
 */
@Entity
@Table(name = "AUDIT_REGISTER")
public class AuditRegister extends UserDateAudit {
	    
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/** auditId: Register Audit Id */
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="audit_id")
private long auditId;

/** requestDate: Request Date */
@Column(name="request_date")
private Date requestDate;

/** operation: Url or description of operation */
@Column(name="operation")
private String operation;

/** Account Relation:  */
@ManyToOne
@JoinColumn(name = "account_id")
private Account account;


public long getAuditId(){
	return auditId;
}

public void setAuditId(long auditId){
	this.auditId=auditId;
}


public Date getRequestDate(){
	return requestDate;
}

public void setRequestDate(Date requestDate){
	this.requestDate=requestDate;
}


public String getOperation(){
	return operation;
}

public void setOperation(String operation){
	this.operation=operation;
}


public Account getAccount(){
	return account;
}

public void setAccount(Account account){
	this.account=account;
}


}
