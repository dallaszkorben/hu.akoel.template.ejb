package hu.akoel.template.ejb.test.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)					//Can be used only in method
public @interface InputSet {

	public String[] value() default "";		//Attribute is "value"
}
