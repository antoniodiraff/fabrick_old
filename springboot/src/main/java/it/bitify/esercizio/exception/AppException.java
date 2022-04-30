//package it.bitify.esercizio.exception;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.client.HttpStatusCodeException;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import it.bitify.esercizio.dto.SandBoxBaseResponse;
//
//public class AppException extends RuntimeException {
//  
//	SandBoxBaseResponse response;
//	
//	public AppException(HttpStatusCodeException e) {
//		String bodyString = e.getResponseBodyAsString();		
//		ObjectMapper mapper = new ObjectMapper();
//		try {
//			response = mapper.readValue(bodyString, SandBoxBaseResponse.class);
//		} catch (JsonProcessingException e1) {
//			e1.printStackTrace();
//		}	
//		
//	}
//
//	public ResponseEntity<SandBoxBaseResponse> getResponse(HttpStatus status) {
//
//		return new ResponseEntity<SandBoxBaseResponse>(this, status);
//    }
//
// 
//}
