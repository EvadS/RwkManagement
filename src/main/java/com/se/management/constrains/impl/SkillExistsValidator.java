package com.se.management.constrains.impl;

import com.se.management.constrains.MessengerTypeExists;
import com.se.management.repository.SkillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * Validator of content type. This is simple and not complete implementation
 * of content type validating. It's based just on <code>String</code> equalsIgnoreCase
 * method.
 */
public class SkillExistsValidator implements ConstraintValidator<MessengerTypeExists, Long> {

    private final Logger logger = LoggerFactory.getLogger(SkillExistsValidator.class);

    private final SkillRepository skillRepository;

    public SkillExistsValidator(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        boolean skillExists =  skillRepository.existsById(value);

        logger.debug("Skill exists validator. Skill with id : {} exists : {} ", value, skillExists);

        return skillExists;
    }
}
