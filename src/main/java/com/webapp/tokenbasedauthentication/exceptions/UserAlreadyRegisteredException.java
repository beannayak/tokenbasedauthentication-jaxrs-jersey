package com.webapp.tokenbasedauthentication.exceptions;

public class UserAlreadyRegisteredException extends Exception {
	private static final long serialVersionUID = 1L;

	public UserAlreadyRegisteredException() {}
	
	public UserAlreadyRegisteredException(String message) {
		super(message);
	}
}
