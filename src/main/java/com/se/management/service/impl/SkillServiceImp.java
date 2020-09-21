package com.se.management.service.impl;

import com.se.management.domain.Skill;
import com.se.management.exception.EntityAlreadyExistsException;
import com.se.management.exception.SkillNotFoundException;

import com.se.management.mapper.SkillMapper;
import com.se.management.model.search.SkillSearch;
import com.se.management.model.request.SkillRequest;
import com.se.management.model.response.SkillResponse;
import com.se.management.repository.SkillRepository;
import com.se.management.repository.specification.SkillSpecification;
import com.se.management.service.SkillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SkillServiceImp implements SkillService {

    private final Logger logger = LoggerFactory.getLogger(SkillService.class);

    private final SkillSpecification skillSpecification;
    private final SkillRepository skillRepository;

    public SkillServiceImp(SkillSpecification skillSpecification, SkillRepository skillRepository) {
        this.skillSpecification = skillSpecification;
        this.skillRepository = skillRepository;
    }

    @Override
    public SkillResponse getById(Long skillId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new SkillNotFoundException(String.format("Can't skill by id : %s.", skillId)));

        return SkillMapper.INSTANCE.SkillToSkillResponse(skill);
    }


    @Override
    public SkillResponse create(SkillRequest skillRequest) {

        // TODO: refactoring, maybe use constraint
        Optional<Skill> skillItemOptional = skillRepository.findTop1ByName(skillRequest.getName());
        if (skillItemOptional.isPresent()) {
            throw new EntityAlreadyExistsException(
                    String.format("Can't save new skill with name : %s. The skill is already exists.", skillRequest.getName()));
        }

        Skill skill =  SkillMapper.INSTANCE.SkillRequestToSkillItem(skillRequest);

        skillRepository.save(skill);

        return SkillMapper.INSTANCE.SkillToSkillResponse(skill);
    }

    @Override
    public SkillResponse update(Long skillId, SkillRequest skillRequest) {
        Optional<Skill> skillItemOptional = skillRepository.findById(skillId);

        if (!skillItemOptional.isPresent()) {
            logger.error("Can't find skill by id : {}", skillId);
            throw new SkillNotFoundException(String.format("Can't find skill by id %s", skillId));
        }

        Skill skill = skillItemOptional.get();

        skill.setName(skillRequest.getName());
        skillRepository.save(skill);

        return SkillMapper.INSTANCE.SkillToSkillResponse(skill);
    }

    @Override
    public boolean delete(Long skillId) {
        Optional<Skill> skillItemOptional = skillRepository.findById(skillId);

        if (!skillItemOptional.isPresent()) {
            logger.error("Can't find skill by id : {}", skillId);
            throw new SkillNotFoundException(String.format("Can't find skill by id %s", skillId));
        }

        skillRepository.delete(skillItemOptional.get());
        return  true;
    }

    @Override
    public Page<SkillResponse> filter(SkillSearch request, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return skillRepository.findAll(skillSpecification.getFilter(request), pageable)
                .map(SkillMapper.INSTANCE::SkillToSkillResponse);
    }

    @Override
    public List<SkillResponse> list() {
        return skillRepository.findAll().stream()
                .map(SkillMapper.INSTANCE::SkillToSkillResponse).collect(Collectors.toList());
    }
}
