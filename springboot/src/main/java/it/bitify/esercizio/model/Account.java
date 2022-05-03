/**************************************************************************
*
* Copyright 2022 (C) Bitify s.r.l.
*
* Created on  : 2022-04-24
* Author      : A. Di Raffaele
* Project Name: esercizio 
* Package     : model
* File Name   : Account.java
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
import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;


/**
 * Created by A. Di Raffaele 
 * Account: Entità
 */
@Entity
@Table(name = "ACCOUNT")
public class Account {
	    
/** accountId: The ID of the account. */
@Id
//@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="account_id")
private long accountId;

/** iban: The IBAN code of the account. */
@Column(name="iban")
private String iban;

/** abiCode: The abiCode code of the account. */
@Column(name="abi_code")
private String abiCode;

/** cabCode: The cabCode code of the account. */
@Column(name="cab_code")
private String cabCode;

/** countryCode: The countryCode code of the account. */
@Column(name="country_code")
private String countryCode;

/** internationalCin: The internationalCin code of the account. */
@Column(name="international_cin")
private String internationalCin;

/** nationalCin: The nationalCin code of the account. */
@Column(name="national_cin")
private String nationalCin;

/** account: The account number. Substring of IBAN code. */
@Column(name="account")
private String account;

/** alias: The alias of the account, if any. */
@Column(name="alias")
private String alias;

/** productName: The account product name. */
@Column(name="product_name")
private String productName;

/** holderName: The full name (or names) of the account holder (or holders). */
@Column(name="holder_name")
private String holderName;

/** activatedDate: The date on which the account was activated. */
@Column(name="activated_date")
private Date activatedDate;

/** currency: The native currency of the account. */
@Column(name="currency")
private String currency;

/** Transaction Relation:  */
@OneToMany(mappedBy="account",cascade=CascadeType.ALL, orphanRemoval = true )
private List<Transaction> transactions;
/** AuditRegister Relation:  */
@OneToMany(mappedBy="account")
@JsonIgnore
private List<AuditRegister> auditRegisters;


public long getAccountId(){
	return accountId;
}

public void setAccountId(long accountId){
	this.accountId=accountId;
}


public String getIban(){
	return iban;
}

public void setIban(String iban){
	this.iban=iban;
}


public String getAbiCode(){
	return abiCode;
}

public void setAbiCode(String abiCode){
	this.abiCode=abiCode;
}


public String getCabCode(){
	return cabCode;
}

public void setCabCode(String cabCode){
	this.cabCode=cabCode;
}


public String getCountryCode(){
	return countryCode;
}

public void setCountryCode(String countryCode){
	this.countryCode=countryCode;
}


public String getInternationalCin(){
	return internationalCin;
}

public void setInternationalCin(String internationalCin){
	this.internationalCin=internationalCin;
}


public String getNationalCin(){
	return nationalCin;
}

public void setNationalCin(String nationalCin){
	this.nationalCin=nationalCin;
}


public String getAccount(){
	return account;
}

public void setAccount(String account){
	this.account=account;
}


public String getAlias(){
	return alias;
}

public void setAlias(String alias){
	this.alias=alias;
}


public String getProductName(){
	return productName;
}

public void setProductName(String productName){
	this.productName=productName;
}


public String getHolderName(){
	return holderName;
}

public void setHolderName(String holderName){
	this.holderName=holderName;
}


public Date getActivatedDate(){
	return activatedDate;
}

public void setActivatedDate(Date activatedDate){
	this.activatedDate=activatedDate;
}


public String getCurrency(){
	return currency;
}

public void setCurrency(String currency){
	this.currency=currency;
}


public List<Transaction> getTransactions(){
	return transactions;
}

public void setTransactions(List<Transaction> transactions){
	this.transactions=transactions;
}


public List<AuditRegister> getAuditRegisters(){
	return auditRegisters;
}

public void setAuditRegisters(List<AuditRegister> auditRegisters){
	this.auditRegisters=auditRegisters;
}


}
