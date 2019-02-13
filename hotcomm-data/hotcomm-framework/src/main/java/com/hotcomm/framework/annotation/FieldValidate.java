package com.hotcomm.framework.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({METHOD, FIELD}) 
@Retention(RUNTIME)
public @interface FieldValidate {
	
	String limit() default "";
	ParamType type() default ParamType.STRING;
	String pattern() default "";
}
