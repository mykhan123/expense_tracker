package com.example.expenseTracker.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
			

		@ExceptionHandler(IllegalArgumentException.class)
		public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex) {
			return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
		
		@ExceptionHandler(NullPointerException.class)
	    public ResponseEntity<Object> handleNullPointer(NullPointerException ex) {
	        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong (NullPointerException)");
	    }
		@ExceptionHandler(Exception.class)
	    public ResponseEntity<Object> handleGeneralException(Exception ex) {
	        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error: " + ex.getMessage());
	    }
		
		
		private ResponseEntity<Object> buildResponse(HttpStatus status, String message) {
	        Map<String, Object> errorDetails = new HashMap<>();
	        errorDetails.put("timestamp", LocalDateTime.now());
	        errorDetails.put("status", status.value());
	        errorDetails.put("error", status.getReasonPhrase());
	        errorDetails.put("message", message);

	        return new ResponseEntity<>(errorDetails, status);
	    }
}
