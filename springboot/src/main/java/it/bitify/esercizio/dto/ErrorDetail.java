package it.bitify.esercizio.dto;

import java.time.LocalDateTime;

import org.springframework.web.context.request.WebRequest;

public class ErrorDetail{

//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	private String code;
//	private String description;
//	
//	
//	public String getDescription() {
//		return description;
//	}
//	public void setDescription(String description) {
//		this.description = description;
//	}
//	public String getCode() {
//		return code;
//	}
//	public void setCode(String code) {
//		this.code = code;
//	} 
//
//	
//	
//	
//	
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final String exception;

    // Will contain exception details(string, list, set)
    private final Object details;

    // Will contain the correlation id
    private final String requestId;

    private final String requestUri;
	
	
    public ErrorDetail(Exception exception, WebRequest request) {
        this.exception = exception.getClass().getSimpleName();
        this.details = exception.getMessage();
        this.requestId = request.getHeader("X-Correlation-ID");
        this.requestUri = request.getDescription(false);

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
	public String getRequestId() {
		return requestId;
	}
	public String getRequestUri() {
		return requestUri;
	}
	
}
