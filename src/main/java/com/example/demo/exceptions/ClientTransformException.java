package com.example.demo.exceptions;

@SuppressWarnings("serial")
public class ClientTransformException extends RuntimeException {

	public enum ErrorCode{
		DUPLICATE_ID_OR_MOBILENUMBER(1000),
		MISSING_REQUEST_PARAMETER(1001),
		NO_OBJECT_FOUND(1002),
		INVALID_ID(1003);
		
		private final int errorCd;
		
		private ErrorCode (int errorCd) {
			this.errorCd = errorCd;
		}
		
		public int ToIntValue() {
			return this.errorCd;
		}
	}
	
	private ErrorCode errorCode;

	public ClientTransformException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public ClientTransformException(String message, Throwable cause,ErrorCode errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public ClientTransformException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

}
