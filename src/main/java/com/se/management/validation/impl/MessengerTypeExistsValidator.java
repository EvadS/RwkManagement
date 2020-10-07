package com.se.management.validation.impl;

import com.se.management.validation.annotation.MessengerTypeExists;
import com.se.management.repository.MessengerTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class MessengerTypeExistsValidator implements ConstraintValidator<MessengerTypeExists, Long> {

    private final Logger logger = LoggerFactory.getLogger(MessengerTypeExistsValidator.class);

    private final MessengerTypeRepository messengerTypeRepository;

    public MessengerTypeExistsValidator(MessengerTypeRepository messengerTypeRepository) {
        this.messengerTypeRepository = messengerTypeRepository;
    }


    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        boolean isExists =  messengerTypeRepository.existsById(value);

        logger.debug("Messenger type exists validator. Messenger with id : {} exists : {} ", value, isExists);

        return isExists;
    }
}
