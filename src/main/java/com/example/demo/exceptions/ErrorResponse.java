package com.example.demo.exceptions;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "error")
public class ErrorResponse
{
    public ErrorResponse(String errorCd,String message) {
        this.message = message;
        this.errorCd = errorCd;
    }
  
    private String message;
    private String errorCd;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrorCd() {
		return errorCd;
	}
	public void setErrorCd(String errorCd) {
		this.errorCd = errorCd;
	}
    
}
