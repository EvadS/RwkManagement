package com.se.management.service.impl;

import com.se.management.domain.MessengerType;
import com.se.management.exception.EntityAlreadyExistsException;
import com.se.management.exception.SkillNotFoundException;
import com.se.management.mapper.MessengerTypeMapper;
import com.se.management.mapper.SkillMapper;
import com.se.management.model.request.MessengerTypeRequest;
import com.se.management.model.response.MessengerTypeResponse;
import com.se.management.model.response.SkillResponse;
import com.se.management.model.search.MessengerTypeSearch;
import com.se.management.repository.MessengerTypeRepository;
import com.se.management.repository.specification.MessengerTypeSpecification;
import com.se.management.service.MessengerTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessengerTypeImpl implements MessengerTypeService {
    private final Logger logger = LoggerFactory.getLogger(MessengerTypeService.class);

    private final MessengerTypeRepository messengerRepo;
    private final MessengerTypeSpecification messengerTypeSpecification;

    public MessengerTypeImpl(MessengerTypeRepository messengerRepo, MessengerTypeSpecification messengerTypeSpecification) {
        this.messengerRepo = messengerRepo;
        this.messengerTypeSpecification = messengerTypeSpecification;
    }

    @Override
    public Page<MessengerTypeResponse> filter(MessengerTypeSearch request, Pageable pageable) {
        return messengerRepo.findAll(messengerTypeSpecification.getFilter(request), pageable)
                .map(MessengerTypeMapper.INSTANCE::MessengerTypeToMessengerTypeResponse);
    }

    @Override
    public MessengerTypeResponse getById(Long id) {
        MessengerType messengerType = messengerRepo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Can't messenger by id : %s.", id)));

        return MessengerTypeMapper.INSTANCE.MessengerTypeToMessengerTypeResponse(messengerType);
    }

    @Override
    public MessengerTypeResponse create(MessengerTypeRequest request) {

        Optional<MessengerType> messengerTypeOpt = messengerRepo.findTop1ByName(request.getName());
        if (messengerTypeOpt.isPresent()) {
            throw new EntityAlreadyExistsException(
                    String.format("Can't save new skill with name: %s. The skill is already exists.", request.getName()));
        }

        MessengerType messengerType = MessengerTypeMapper.INSTANCE.MessengerTypeRequestToMessengerType(request);
        messengerRepo.save(messengerType);

        logger.debug("Added new messenger type : {} ", messengerType);

        return MessengerTypeMapper.INSTANCE.MessengerTypeToMessengerTypeResponse(messengerType);

    }

    @Override
    public MessengerTypeResponse update(Long id, MessengerTypeRequest request) {
//        Optional<MessengerType> messengerTypeOpt = messengerRepo.findById(id);
//
//        if (!messengerTypeOpt.isPresent()) {
//            logger.error("Can't find messenger type by id : {}", id);
//            throw new SkillNotFoundException(String.format("Can't find messenger type by id %s", id));
//        }
//
//        MessengerType messengerType = messengerTypeOpt.get();
//
//        messengerType.setName(request.getName());
//        messengerRepo.save(messengerType);
//
//        logger.debug("Messenger id : {} was updated to: {} ",id, messengerType);
//
//        return MessengerTypeMapper.INSTANCE.MessengerTypeToMessengerTypeResponse(messengerType);
return null;
    }

    @Override
    public void delete(Long id) {
        Optional<MessengerType> messengerTypeOpt = messengerRepo.findById(id);

        if (!messengerTypeOpt.isPresent()) {
            logger.error("Can't find messenger type by id : {}", id);
            throw new SkillNotFoundException(String.format("Can't find  messenger type by id %s", id));
        }


        messengerRepo.delete(messengerTypeOpt.get());

        logger.debug("Messenger id : {} has been deleted.",id);
    }

    @Override
    public List<MessengerTypeResponse> list() {
        return messengerRepo.findAll().stream()
                .map(MessengerTypeMapper.INSTANCE::MessengerToMessengerTypeResponse).collect(Collectors.toList());
    }
}
