package com.webapp.tokenbasedauthentication.exceptions;

public class UserNotRegisteredException extends Exception {
	private static final long serialVersionUID = 1L;

	public UserNotRegisteredException() {}
	
	public UserNotRegisteredException(String message) {
		super(message);
	}
}
