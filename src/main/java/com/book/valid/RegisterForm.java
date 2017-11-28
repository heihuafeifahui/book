package com.book.valid;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.book.util.FieldMatch;



@FieldMatch(first = "npassword", second = "cpassword", message = "user.password.notmatch")
public class RegisterForm {
	
	@NotBlank(message = "user.account.isnull")
	@Pattern(regexp = "[a-zA-Z0-9_@]{3,30}.[a-zA-Z0-9]{1,}", message = "user.account.format.error")
	private String account;
	
	@NotBlank(message = "register.must.agree.terms")
	@Pattern(regexp = "yes{1}", message = "register.must.agree.terms")
	private String agree;
	
	private String vcode;
	
	private String email;
	
	@NotBlank(message="user.passwd.isnull") @Size(min=6, max=16, message="user.passwd.length.error")
	private String npassword;
	
	@NotBlank(message="user.passwd.isnull") @Size(min=6, max=16, message="user.passwd.length.error")
	private String cpassword;
	
	private String regCode;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAgree() {
		return agree;
	}

	public void setAgree(String agree) {
		this.agree = agree;
	}

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	public String getNpassword() {
		return npassword;
	}

	public void setNpassword(String npassword) {
		this.npassword = npassword;
	}

	public String getCpassword() {
		return cpassword;
	}

	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}

	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
