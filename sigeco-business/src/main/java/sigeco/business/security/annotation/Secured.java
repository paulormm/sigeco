package sigeco.business.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import sigeco.model.Permission;

/**
 * Annotation for methods that should be secured permission-wise. 
 * 
 * @author julien
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Secured {

	/**
	 * The permission required to invoke this method.
	 * 
	 * Default is Permission.ADMIN
	 */
	Permission[] value() default {Permission.ADMIN };
	
}
