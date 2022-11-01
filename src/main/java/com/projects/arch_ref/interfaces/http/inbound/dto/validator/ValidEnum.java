package com.projects.arch_ref.interfaces.http.inbound.dto.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EnumValidatorImpl.class)
public @interface ValidEnum {
    Class<? extends Enum<?>> enumType();
    String message() default "must be any of {enumType}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
