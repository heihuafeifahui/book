package com.book.util;

import java.util.Arrays;
import java.util.Objects;

public class ServiceException extends RuntimeException{
	private static final long serialVersionUID=-4754310636975621327L;
	//字段
	private final String field;
	//信息
	private final String msg;
	//参数
	private final Object[] params;
	
	public ServiceException(String msg) {
		super(msg);
		this.field="message";
		this.msg=msg;
		this.params=new Object[0];
	}
	public ServiceException(String field,String msg) {
		super(msg);
		this.field=field;
		this.msg=msg;
		this.params=new Object[0];
	}
//	Object ...objects这种参数定义是在不确定方法参数的情况下的一种多态表现形式。即这个方法可以传递多个参数，这个参数的个数是不确定的。
	public ServiceException(String msg,Object... params) {
		super(msg, parseCause(params));
		this.field="";
		this.msg=msg;
		this.params=parseParams(params);
	}
	

	public ServiceException(String field,String msg,Object... params) {
		super(msg, parseCause(params));
		this.field=field;
		this.msg=msg;
		this.params=parseParams(params);
		
	}
	//Throwable 类是 Java 语言中所有错误或异常的超类
	private static Throwable parseCause(Object[] params) {
		if(Objects.nonNull(params) && params.length > 0) {
			Object lastParam = params[params.length - 1];
			if(Objects.nonNull(lastParam) && lastParam instanceof Throwable) {
				return (Throwable) lastParam;
			}
		}
		return null;
	}
	
	private Object[] parseParams(Object[] params) {
		if(Objects.nonNull(params) && params.length > 0) {
			Object lastParam = params[params.length - 1];
			if(Objects.nonNull(lastParam) && lastParam instanceof Throwable) {
				return Arrays.copyOfRange(params, 0, params.length - 1);
			}else {
				return params;
			}
		}else {
			return new Object[0];
		}
	}
	public String getField() {
		return field;
	}
	public String getMsg() {
		return msg;
	}
	public Object[] getParams() {
		return params;
	}
	
}
