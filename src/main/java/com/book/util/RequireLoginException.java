package com.book.util;

public class RequireLoginException extends ServiceException {
	private static final long serialVersionUID = 8705532697610372523L;

	public RequireLoginException() {
		super("user.require.login");
	}
	
	public RequireLoginException(String msg) {
		super(msg);
	}

}
