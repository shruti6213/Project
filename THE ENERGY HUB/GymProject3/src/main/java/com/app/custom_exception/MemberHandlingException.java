package com.app.custom_exception;

@SuppressWarnings("serial")
public class MemberHandlingException extends RuntimeException {
	public MemberHandlingException(String msg) {
		super(msg);
	}

}
