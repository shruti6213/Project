package com.app.custom_exception;

@SuppressWarnings("serial")
public class PackageHandlingException extends RuntimeException {
public PackageHandlingException(String msg) {
	super(msg);
}
}
