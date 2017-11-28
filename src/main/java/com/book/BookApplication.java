package com.book;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
public class BookApplication {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	public static void main(String []args) {
		SpringApplication.run(BookApplication.class, args);
	}
	//声明Bean，注入IoC
		@Bean 
		//让web支持国际化,识别首选区域,并根据区域显示内容
		public LocaleResolver localeResolver() {
//			通过检验用户会话中预置的属性来解析区域  
			SessionLocaleResolver slr = new SessionLocaleResolver();
			slr.setDefaultLocale(Locale.CHINA);
			return slr;
		}

		@Bean
		public ReloadableResourceBundleMessageSource messageSource() {
			ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
			//创建国际化配置
			messageSource.setBasename("classpath:i18n/messages");
			//使用编码作为默认信息
			messageSource.setUseCodeAsDefaultMessage(true);
			//		每小时刷新一次
			messageSource.setCacheSeconds(3600); //
			return messageSource;
		}
		@Bean
		//Session共享
		public CookieSerializer cookieSerializer() {
			DefaultCookieSerializer serializer = new DefaultCookieSerializer();
			serializer.setCookieName("JSESSIONID");
			serializer.setCookiePath("/");
			serializer.setDomainName("localhost");
			log.info("cookieSerializer");
			return serializer;
		}
}
