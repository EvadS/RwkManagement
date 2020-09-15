package com.se.management.service.impl;

import com.se.management.domain.SkillItem;
import com.se.management.exception.IncorrectSkillException;
import com.se.management.exception.SkillNotFoundException;
import com.se.management.mapper.SkillItemMapper;
import com.se.management.model.SkillSearch;
import com.se.management.model.request.SkillItemRequest;
import com.se.management.model.request.SkillRequest;
import com.se.management.model.response.SkillItemResponse;
import com.se.management.repository.SkillItemRepository;
import com.se.management.repository.specification.SkillItemSpecification;
import com.se.management.service.SkillItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillItemServiceImpl implements SkillItemService {

    private final Logger logger = LoggerFactory.getLogger(SkillItemService.class);

    private final SkillItemSpecification skillItemSpecification;
    private final SkillItemRepository skillItemRepository;

    public SkillItemServiceImpl(SkillItemSpecification skillItemSpecification, SkillItemRepository skillItemRepository) {
        this.skillItemSpecification = skillItemSpecification;
        this.skillItemRepository = skillItemRepository;
    }

    @Override
    public SkillItemResponse getById(Long skillId) {
        SkillItem skillItem = skillItemRepository.findById(skillId)
                .orElseThrow(() ->
                        new SkillNotFoundException(String.format("Can't skill by id : %s.", skillId)));

        return SkillItemMapper.INSTANCE.SkillToSkillResponse(skillItem);
    }


    @Override
    public SkillItemResponse create(SkillItemRequest skillRequest) {

        // TODO: refactoring, maybe use constraint
        Optional<SkillItem> skillItemOptional = skillItemRepository.findTop1ByName(skillRequest.getName());
        if (skillItemOptional.isPresent()) {
            throw new IncorrectSkillException(
                    String.format("Can't save new skill with name : %s. The skill is already exists.", skillRequest.getName()));
        }

        SkillItem skillItem =  SkillItemMapper.INSTANCE.SkillRequestToSkillItem(skillRequest);

        skillItemRepository.save(skillItem);

        return SkillItemMapper.INSTANCE.SkillToSkillResponse(skillItem);
    }

    @Override
    public SkillItemResponse update(Long skillId, SkillItemRequest skillRequest) {
        Optional<SkillItem> skillItemOptional = skillItemRepository.findById(skillId);

        if (!skillItemOptional.isPresent()) {
            logger.error("Can't find skill by id : {}", skillId);
            throw new SkillNotFoundException(String.format("Can't find skill by id %s", skillId));
        }

        SkillItem skillItem = skillItemOptional.get();

        skillItem.setName(skillRequest.getName());
        skillItemRepository.save(skillItem);

        return SkillItemMapper.INSTANCE.SkillToSkillResponse(skillItem);
    }

    @Override
    public boolean delete(Long skillId) {
        Optional<SkillItem> skillItemOptional = skillItemRepository.findById(skillId);

        if (!skillItemOptional.isPresent()) {
            logger.error("Can't find skill by id : {}", skillId);
            throw new SkillNotFoundException(String.format("Can't find skill by id %s", skillId));
        }

        skillItemRepository.delete(skillItemOptional.get());
        return  true;
    }

    @Override
    public Page<SkillItemResponse> filter(SkillSearch request, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return skillItemRepository.findAll(skillItemSpecification.getFilter(request), pageable)
                .map(SkillItemMapper.INSTANCE::SkillToSkillResponse);
    }
}
