package com.book;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//处理全局异常
@ControllerAdvice
public class GlobalAdvice {
	//初始化日志对象，打印GlobalAdvic类中的日志信息
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalAdvice.class);
	
	@Autowired
	//提供消息处理的功能(i18n)
	private MessageSource messageSource;


	//异常页面控制 
	// Controller中任何一个方法发生异常，一定会被这个方法拦截到。然后，输出日志。 封装Map并返回，页面上得到status为false。
	@ResponseBody
	@ExceptionHandler(BindException.class)
	public Map<String, Object> validExceptionHandler(BindException ex) {
		//解析当前用户区域
		final Locale locale = LocaleContextHolder.getLocale();
		
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		return new HashMap<String, Object>() {
			private static final long serialVersionUID = -3222861572243435035L;
			{
				//遍历异常
				for (FieldError error :fieldErrors) {
					
					Object value = ex.getFieldValue(error.getField());
					String message = messageSource.getMessage(error.getDefaultMessage(), new Object[] { value },
							locale);
					//通过异常id存取异常
					put(error.getField(), message);
				}
			}
		};
	}
}
