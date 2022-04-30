package it.bitify.esercizio.dto;

import java.util.Collection;
import java.util.LinkedHashMap;

import it.bitify.esercizio.model.Transaction;
import it.bitify.esercizio.util.AppConstants;

public class SandBoxBaseResponse extends LinkedHashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1811417593508757374L;

	public SandBoxBaseResponse setPayload(Collection<Transaction> transactionList) {
		this.put(AppConstants.STATUS, "OK"); 
		this.put(AppConstants.PAYLOAD, transactionList); 
		return this;
	}

}
