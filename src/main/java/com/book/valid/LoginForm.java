package com.book.valid;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class LoginForm {
	@NotBlank(message = "user.account.isnull")
	@Pattern(regexp = "[a-zA-Z0-9_@]{3,30}.[a-zA-Z0-9]{1,}", message = "user.account.format.error")
	private String account;
	
	private String vcode;
	
	@NotBlank(message = "user.passwd.isnull")
	@Size(min = 6, message = "user.passwd.length.error")
	private String password;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
