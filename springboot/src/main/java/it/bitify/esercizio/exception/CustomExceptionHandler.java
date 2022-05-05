package it.bitify.esercizio.exception;

import java.awt.TrayIcon.MessageType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionTimedOutException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import it.bitify.esercizio.dto.SandBoxBaseResponse;


@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<SandBoxBaseResponse> restclientExceptionHandler(RestClientException exception, WebRequest request){
    	return getException(exception, request, MessageType.ERROR, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<SandBoxBaseResponse> methodArgumentTypeMismatchExceptionExceptionHandler(MethodArgumentTypeMismatchException exception, WebRequest request){
    	return getException(exception, request, MessageType.ERROR, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(TransactionTimedOutException.class)
    public ResponseEntity<SandBoxBaseResponse> transactionTimedOutExceptionExceptionExceptionHandler(TransactionTimedOutException exception, WebRequest request){
    	return getException(exception, request, MessageType.ERROR, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<SandBoxBaseResponse> httpStatusCodeExceptionHandler(HttpStatusCodeException exception, WebRequest request){
    	return getException(exception, request, MessageType.ERROR, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<SandBoxBaseResponse> httpClientErrorExceptionHandler(HttpServerErrorException exception, WebRequest request){
    	return getException(exception, request, MessageType.ERROR, exception.getStatusCode());
    }
    
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestException> httpClientErrorExceptionHandler(BadRequestException exception, WebRequest request){
    	return getExceptionCustom(exception, request, MessageType.ERROR, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * 
     * @param exception
     * @param request
     * @param error
     * @param badRequest
     * @return
     */
	private ResponseEntity<BadRequestException> getExceptionCustom(BadRequestException exception, WebRequest request,
			MessageType error, HttpStatus badRequest) {
		return new ResponseEntity<BadRequestException>(new BadRequestException(exception.getMessage(), exception.getCause()), HttpStatus.BAD_REQUEST);
	}

	/**
	 * 
	 * @param exception
	 * @param request
	 * @param messageLevelType
	 * @param httpStatus
	 * @return
	 */
	private ResponseEntity<SandBoxBaseResponse> getException(RuntimeException exception, WebRequest request, MessageType messageLevelType, HttpStatus httpStatus){
		SandBoxBaseResponse response = new SandBoxBaseResponse(); 
		response.setError(exception.getMessage());
		return new ResponseEntity<SandBoxBaseResponse>(response, httpStatus);
	}
	
}