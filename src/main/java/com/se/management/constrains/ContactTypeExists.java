package com.se.management.constrains;

import com.se.management.constrains.impl.SkillExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;



import javax.validation.Constraint;
        import javax.validation.Payload;
        import java.lang.annotation.*;

        import static java.lang.annotation.ElementType.*;
        import static java.lang.annotation.ElementType.TYPE_USE;
        import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {SkillExistsValidator.class})
public @interface ContactTypeExists {

    String message() default "messenger.invalid}";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}