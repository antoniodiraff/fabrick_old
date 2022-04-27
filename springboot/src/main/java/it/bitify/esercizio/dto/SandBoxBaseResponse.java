package it.bitify.esercizio.dto;

import java.util.LinkedHashMap;

public class SandBoxBaseResponse extends LinkedHashMap<String, Object>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	String status; 
	ErrorDetail error; 
	Object payload;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ErrorDetail getError() {
		return error;
	}
	public void setError(ErrorDetail error) {
		this.error = error;
	}
	public Object getPayload() {
		return payload;
	}
	public void setPayload(Object payload) {
		this.payload = payload;
	}
	
	
	
	
}
