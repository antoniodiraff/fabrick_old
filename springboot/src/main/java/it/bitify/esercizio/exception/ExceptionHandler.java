package it.bitify.esercizio.exception;

import java.awt.TrayIcon.MessageType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionTimedOutException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import it.bitify.esercizio.dto.ErrorDetail;
import it.bitify.esercizio.dto.SandBoxBaseResponse;
import it.bitify.esercizio.util.AppConstants;


@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {


	@org.springframework.web.bind.annotation.ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<SandBoxBaseResponse> resorurceNotFoundExceptionHandler(ResourceNotFoundException exception, WebRequest request){
    	return getException(exception, request, MessageType.ERROR, HttpStatus.NOT_FOUND);
    }
	
    @org.springframework.web.bind.annotation.ExceptionHandler(RestClientException.class)
    public ResponseEntity<SandBoxBaseResponse> restclientExceptionHandler(RestClientException exception, WebRequest request){
    	return getException(exception, request, MessageType.ERROR, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<SandBoxBaseResponse> methodArgumentTypeMismatchExceptionExceptionHandler(MethodArgumentTypeMismatchException exception, WebRequest request){
    	return getException(exception, request, MessageType.ERROR, HttpStatus.BAD_REQUEST);
    }
    
    @org.springframework.web.bind.annotation.ExceptionHandler(TransactionTimedOutException.class)
    public ResponseEntity<SandBoxBaseResponse> transactionTimedOutExceptionExceptionExceptionHandler(TransactionTimedOutException exception, WebRequest request){
    	return getException(exception, request, MessageType.ERROR, HttpStatus.BAD_REQUEST);
    }
    
	private ResponseEntity<SandBoxBaseResponse> getException(RuntimeException exception, WebRequest request, MessageType messageLevelType, HttpStatus httpStatus){
		SandBoxBaseResponse response = new SandBoxBaseResponse(); 
		response.put(AppConstants.ERROR, new ErrorDetail(exception, request)); 
		return new ResponseEntity<SandBoxBaseResponse>(response, httpStatus);
	}
	
}