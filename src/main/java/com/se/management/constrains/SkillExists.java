package com.se.management.constrains;

import com.se.management.constrains.impl.SkillExistsValidator;

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
public @interface SkillExists {

    String message() default "Skill doesn't exists";//"{id.invalid}"

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}