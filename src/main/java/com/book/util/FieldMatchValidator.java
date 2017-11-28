package com.book.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch,Object>{
	
	private String firstFieldName;
	private String secondFieldName;
	private String message;
	//获取要验证的两个字段的名称
	public void initialize(final FieldMatch constraintAnnotation) {
		firstFieldName=constraintAnnotation.first();
		secondFieldName=constraintAnnotation.second();
		message=constraintAnnotation.message();
	}
	//编写验证规则
	public boolean isValid(final Object value,final ConstraintValidatorContext context) {
		//默认匹配为错误
		boolean matches=false;
		try {
			//通过反射,得到value对象的firstFieldName
			final Object firstObj=BeanUtils.getProperty(value, firstFieldName);
			final Object secondObj=BeanUtils.getProperty(value, secondFieldName);
			//如果匹配的两个对象都为空或者第一个对象不为空且与第二个对象相等，保存到matches
			matches=firstObj == null && secondObj == null ||
					firstObj != null && firstObj.equals(secondObj);
		}catch(final Exception ignore) {
			//忽视
		}
		//验证出错的消息
		if(!matches) {
			//
			context.disableDefaultConstraintViolation();
			//在模板上通过message显示违法约束的错误，属性节点在第一个对象上
			context.buildConstraintViolationWithTemplate(message).addPropertyNode(firstFieldName)
			.addConstraintViolation();
		}
		return matches;
	}

}
