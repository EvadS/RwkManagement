package com.se.management.controller;


import com.se.management.controller.base.SkillsControllerBase;
import com.se.management.model.request.SkillRequest;
import com.se.management.model.search.SkillSearch;
import com.se.management.model.request.SkillRequest;
import com.se.management.model.response.SkillResponse;
import com.se.management.service.SkillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
//TODO: check 
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4201"})
@RequestMapping("/skills")
public class SkillsController implements SkillsControllerBase {
    private final SkillService skillService;
    private final Logger logger = LoggerFactory.getLogger(SkillsController.class);
    public SkillsController(SkillService skillService) {
        this.skillService = skillService;
    }

    @Override
    public ResponseEntity<Page<SkillResponse>> filter(@NotNull SkillSearch request, Pageable pageable) {
        logger.debug("Handle filter  request ");
        Page<SkillResponse> responsePage = skillService.filter(request, pageable);
        return ResponseEntity.ok(responsePage);
    }

    @Override
    public ResponseEntity<List<SkillResponse>> getAll() {
        logger.debug("Handle getAll  request ");
        List<SkillResponse> skillResponseList = skillService.list();
        return ResponseEntity.ok(skillResponseList);
    }


    @Override
    public ResponseEntity<SkillResponse> getById(Long skillId) {
        logger.debug("Handle getById  request  id: {}", skillId);
        SkillResponse skillResponse = skillService.getById(skillId);
        return ResponseEntity.ok(skillResponse);
    }

    @Override
    public ResponseEntity<SkillResponse> update(@NotNull Long skillId, SkillRequest skillRequest) {
        logger.debug("Handle update request  id: {}", skillId);
        SkillResponse skillResponse = skillService.update(skillId, skillRequest);
        return new ResponseEntity<>(skillResponse, HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<SkillResponse> create(@Valid @RequestBody SkillRequest skillRequest) {
        logger.debug("Handle create request: {} ", skillRequest);

        SkillResponse skillResponse = skillService.create(skillRequest);
        return new ResponseEntity<>(skillResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<SkillResponse> delete(@NotNull Long skillId) {
        logger.debug("Handle delete request  id: {}", skillId);
        boolean bannerResponse = skillService.delete(skillId);
        return new ResponseEntity(bannerResponse, HttpStatus.ACCEPTED);
    }
}
