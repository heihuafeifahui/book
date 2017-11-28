package com.book.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.book.util.PasswordUtil;

@Entity
@Table(name="user")
public class User {
	@Id
//	@Column持久化映射表字段
	//unique=true是指这个字段的值在这张表里不能重复，所有记录值都要唯一
	//nullable=false是这个字段在保存时必需有值，不能还是null值就调用save去保存入库
	//length属性表示字段的长度，当字段的类型为varchar时，该属性才有效，默认为255个字符。
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;
	
	@Column(nullable=false)
	@NotEmpty(message="user.account.notnull")
	@Size(min=3,max=30,message="user.account.size")
	//@Pattern创建正则表达式的匹配模式
	//@Pattern(regexp = "[a-zA-Z0-9_@]{3,30}.[a-zA-Z0-9]{1,}", message = "user.account.illegal")
	private String account;
	private String password;
	@Email(message="*Please provide a valid Email")
	private String email;
	private String mobile;
	
	public void init() {
		this.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		this.setPassword(PasswordUtil.createPassword(password));
	}
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	

}
