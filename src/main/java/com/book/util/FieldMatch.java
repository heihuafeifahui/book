package com.book.util;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


//@Target：定义注解的作用目标 
//目标类型为注解
@Target({ TYPE, ANNOTATION_TYPE })
//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Retention(RUNTIME)
//表示哪个验证器提供验证
@Constraint(validatedBy = FieldMatchValidator.class)
//说明该注解将被包含在javadoc中
@Documented
//定义注解类
public @interface FieldMatch{
	// 约束注解验证时的输出消息
	String message() default "{constraints.fieldmatch}";
	// 约束注解在验证时所属的组别
	Class<?>[] groups() default {};
	// 约束注解的有效负载
	Class<? extends Payload>[] payload() default {};
	/**
	 * @return The first field
	 */
	String first();

	/**
	 * @return The second field
	 */
	String second();
	

	@Target({ TYPE, ANNOTATION_TYPE })
	@Retention(RUNTIME)
	@Documented
	@interface List{
		FieldMatch[] value();
	}

}
