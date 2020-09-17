package com.se.management.constrains;

import com.se.management.constrains.impl.SkillTypeExistsValidator;

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
@Constraint(validatedBy = {SkillTypeExistsValidator.class})
public @interface SkillTypeExists {

    String message() default "{skill.id.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}