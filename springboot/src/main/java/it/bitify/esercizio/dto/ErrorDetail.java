package it.bitify.esercizio.dto;

import java.time.LocalDateTime;

import org.springframework.web.context.request.WebRequest;

public class ErrorDetail{
    private  LocalDateTime timestamp = LocalDateTime.now();
    private  String exception;

    // Will contain exception details(string, list, set)
    private Object details;

    public ErrorDetail(Exception exception) {
        this.exception = exception.getClass().getSimpleName();
        this.details = exception.getMessage();
    
    }

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getException() {
		return exception;
	}

	public Object getDetails() {
		return details;
	}
    

	public ErrorDetail() {
			super();
			// TODO Auto-generated constructor stub
	}

}
