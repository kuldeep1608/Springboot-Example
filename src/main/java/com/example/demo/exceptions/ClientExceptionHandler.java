package com.example.demo.exceptions;


import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;

@ControllerAdvice
public class ClientExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ClientTransformException.class)
    public final ResponseEntity<ErrorResponse> handleExceptions(ClientTransformException ex, WebRequest request) {
        ErrorResponse error = null ;
        switch (ex.getErrorCode()) {
		case DUPLICATE_ID_OR_MOBILENUMBER:{
			new ErrorResponse(ex.getErrorCode().toString(), ex.getMessage());
	        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		case MISSING_REQUEST_PARAMETER:{
			new ErrorResponse(ex.getErrorCode().toString(), ex.getMessage());
	        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
		case NO_OBJECT_FOUND:{
			new ErrorResponse(ex.getErrorCode().toString(), ex.getMessage());
	        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
		case INVALID_ID:{
			new ErrorResponse(ex.getErrorCode().toString(), ex.getMessage());
	        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		default:
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@ExceptionHandler(MismatchedInputException.class)
    public final ResponseEntity<ErrorResponse> handleMismatchedInputException(MismatchedInputException ex, WebRequest request) {
		ErrorResponse error = new ErrorResponse(ex.getLocalizedMessage(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}
