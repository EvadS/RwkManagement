package com.se.management.constrains.impl;

import com.se.management.constrains.SkillTypeExists;
import com.se.management.repository.SkillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SkillTypeExistsValidator implements ConstraintValidator<SkillTypeExists, Long> {

    private final Logger logger = LoggerFactory.getLogger(SkillTypeExistsValidator.class);

    private final SkillRepository skillRepository;

    public SkillTypeExistsValidator(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }


    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        boolean isExists =  skillRepository.existsById(value);
        logger.debug("Skill type exists validator. Messenger with id : {} exists : {} ", value, isExists);

        return isExists;
    }
}
