package com.se.management.controller;


import com.se.management.controller.base.SkillsControllerBase;
import com.se.management.model.SkillSearch;
import com.se.management.model.request.SkillRequest;
import com.se.management.model.response.SkillResponse;
import com.se.management.service.SkillService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/skills")
public class SkillsController implements SkillsControllerBase {
    private final SkillService skillService;

    public SkillsController(SkillService skillService) {
        this.skillService = skillService;
    }

    @Override
    public ResponseEntity<Page<SkillResponse>> filter(@NotNull SkillSearch request, Pageable pageable) {
        Page<SkillResponse> bannerResponseList = skillService.filter(request, pageable);
        return ResponseEntity.ok(bannerResponseList);
    }

    @Override
    public ResponseEntity<SkillResponse> getSkillById(Long skillId) {
        SkillResponse skillResponse = skillService.getById(skillId);
        return ResponseEntity.ok(skillResponse);
    }

    @Override
    public ResponseEntity<SkillResponse> updateSkill(@NotNull Long skillId, SkillRequest skillRequest) {
        SkillResponse skillResponse = skillService.update(skillId, skillRequest);
        return new ResponseEntity<>(skillResponse, HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<SkillResponse> createSkill(@Valid @RequestBody SkillRequest skillRequest) {

        SkillResponse skillResponse = skillService.create(skillRequest);
        return new ResponseEntity<>(skillResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<SkillResponse> deleteSkill(@NotNull Long skillId) {
        boolean bannerResponse = skillService.delete(skillId);
        return new ResponseEntity(bannerResponse, HttpStatus.ACCEPTED);
    }
}
