package it.bitify.esercizio.dto;

import java.util.LinkedHashMap;

public class SandBoxBaseResponse extends LinkedHashMap<String, Object>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String status; 
	Error error; 
	Object payload;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	public Object getPayload() {
		return payload;
	}
	public void setPayload(Object payload) {
		this.payload = payload;
	}
	public SandBoxBaseResponse() {
		super();
		// TODO Auto-generated constructor stub
	} 
	
	
	
	
}
