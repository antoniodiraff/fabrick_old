/**************************************************************************
*
* Copyright 2022 (C) Bitify s.r.l.
*
* Created on  : 2022-04-24
* Author      : A. Di Raffaele
* Project Name: esercizio 
* Package     : model
* File Name   : Transaction.java
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
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.math.BigDecimal;


/**
 * Created by A. Di Raffaele 
 * Transaction: Entità
 */
@Entity
@Table(name = "TRANSACTION")
public class Transaction {
	    
/** transactionId: The ID of the transaction. This is a unique ID for the transaction, valid to identify a transaction across all of your accounts provided by Banca Sella. */
@Id
//@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="transaction_id")
private long transactionId;

/** operationId: The ID of the accounting operation. This ID matches multiple logically connected transactions (e.g., the money transfer with its fees). */
@Column(name="operation_id")
private long operationId;

/** accountingDate: The date on which the transaction was accounted on the account. */
@Column(name="accounting_date")
private Date accountingDate;

/** valueDate: The value date of the transaction. */
@Column(name="value_date")
private Date valueDate;

/** amount: The amount of the transaction. */
@Column(name="amount")
private BigDecimal amount;

/** currency: The currency of the transaction. */
@Column(name="currency")
private String currency;

/** description: The description of the transaction. */
@Column(name="description")
private String description;

/** Type Relation:  */
@ManyToOne
@JoinColumn(name = "type_id")
private Type type;

/** Account Relation:  */
@ManyToOne
@JoinColumn(name = "account_id")
@JsonIgnore
private Account account;

public long getTransactionId(){
	return transactionId;
}

public void setTransactionId(long transactionId){
	this.transactionId=transactionId;
}


public long getOperationId(){
	return operationId;
}

public void setOperationId(long operationId){
	this.operationId=operationId;
}


public Date getAccountingDate(){
	return accountingDate;
}

public void setAccountingDate(Date accountingDate){
	this.accountingDate=accountingDate;
}


public Date getValueDate(){
	return valueDate;
}

public void setValueDate(Date valueDate){
	this.valueDate=valueDate;
}


public BigDecimal getAmount(){
	return amount;
}

public void setAmount(BigDecimal amount){
	this.amount=amount;
}


public String getCurrency(){
	return currency;
}

public void setCurrency(String currency){
	this.currency=currency;
}


public String getDescription(){
	return description;
}

public void setDescription(String description){
	this.description=description;
}


public Type getType(){
	return type;
}

public void setType(Type type){
	this.type=type;
}


public Account getAccount(){
	return account;
}

public void setAccount(Account account){
	this.account=account;
}


}
