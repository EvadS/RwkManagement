package com.se.management.validation.annotation;

import com.se.management.validation.impl.MessengerTypeExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {MessengerTypeExistsValidator.class})
public @interface MessengerTypeExists {

    String message() default "{messenger.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}