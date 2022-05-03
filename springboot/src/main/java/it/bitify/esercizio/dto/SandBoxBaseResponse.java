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

	/**
	 * 
	 * @param list
	 * @return
	 */
	public SandBoxBaseResponse setPayload(Collection<Object> list) {
		this.put(AppConstants.STATUS, "OK");
		this.put(AppConstants.PAYLOAD, list);
		return this;
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public SandBoxBaseResponse setPayload(Object obj) {
		this.put(AppConstants.STATUS, "OK");
		this.put(AppConstants.PAYLOAD, obj);
		return this;
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public SandBoxBaseResponse setError(Object obj) {
		this.put(AppConstants.STATUS, "KO");
		this.put(AppConstants.ERROR, obj);
		return this;
	}
}
