package eu.sifem.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//TODO rewrite or remove
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //on class level
public @interface HowTranslateMe {
	String method();
	String rdfhashcode();
	String ignoreRdfhashcodeIfAttributeIsFilled();
}
