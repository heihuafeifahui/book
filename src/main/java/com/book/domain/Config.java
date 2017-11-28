package com.book.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.alibaba.fastjson.JSON;
import com.book.util.ID;

@Entity
@Table(name="config")
public class Config {
	private static final long serialVersionUID=1801219483748426473L;
	
	@Id
	@Column(name="id",unique=true,nullable=false,length=32)
	private String id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user")
	@NotFound(action=NotFoundAction.IGNORE)
	private User user;
	
	private String property;
	private String valuejson;
	@Transient
	private List<String> values = new ArrayList<String>();

	public List<String> getValues() {
		try {
			values = JSON.parseArray(this.getValuejson(), String.class);
			if (null == values)
				values = new ArrayList<String>();
		} catch (Exception e) {
		}
		return values;
	}
	
	public List<Contact> getContacts() {
		List<Contact> list = new ArrayList<Contact>();
		try {
			list = JSON.parseArray(this.getValuejson(), Contact.class);
		} catch (Exception e) {
		}
		return list;
	}

	public void setValues(List<String> values) {
		this.values = values;
		this.valuejson = JSON.toJSONString(values);
	}

	public void init(User user, String property, Set<String> values) {
		this.id = ID.uuid();
		this.user = user;
		this.property = property;
		this.valuejson = JSON.toJSONString(values);
	}
	
	public void init(User user, String property, String valuejson) {
		this.id = ID.uuid();
		this.user = user;
		this.property = property;
		this.valuejson = valuejson;
	}

	public Config() {
	}
	
	public Config(String property, String value) {
		this.property = property;
		this.values.add(value);
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getProperty() {
		return this.property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getValuejson() {
		return valuejson;
	}

	public void setValuejson(String valuejson) {
		this.valuejson = valuejson;
	}
	
	
	
}
