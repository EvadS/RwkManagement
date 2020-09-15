package com.se.management.controller;


import com.se.management.controller.base.SkillsControllerBase;
import com.se.management.domain.SkillItem;
import com.se.management.model.SkillSearch;
import com.se.management.model.request.SkillItemRequest;
import com.se.management.model.request.SkillRequest;
import com.se.management.model.response.SkillItemResponse;
import com.se.management.service.SkillItemService;
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
    private final SkillItemService skillItemService;

    public SkillsController(SkillItemService skillItemService) {
        this.skillItemService = skillItemService;
    }

    @Override
    public ResponseEntity<Page<SkillItemResponse>> filter(@NotNull SkillSearch request, Pageable pageable) {
        Page<SkillItemResponse> bannerResponseList = skillItemService.filter(request, pageable);
        return ResponseEntity.ok(bannerResponseList);
    }

    @Override
    public ResponseEntity<SkillItemResponse> getSkillById(Long skillId) {
        SkillItemResponse skillItemResponse = skillItemService.getById(skillId);
        return ResponseEntity.ok(skillItemResponse);
    }

    @Override
    public ResponseEntity<SkillItemResponse> updateSkill(@NotNull Long skillId, SkillItemRequest skillRequest) {
        SkillItemResponse skillItemResponse = skillItemService.update(skillId, skillRequest);
        return new ResponseEntity<>(skillItemResponse, HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<SkillItemResponse> createSkill(@Valid @RequestBody SkillItemRequest skillRequest) {

        SkillItemResponse skillItemResponse = skillItemService.create(skillRequest);
        return new ResponseEntity<>(skillItemResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<SkillItemResponse> deleteSkill(@NotNull Long skillId) {
        boolean bannerResponse = skillItemService.delete(skillId);
        return new ResponseEntity(bannerResponse, HttpStatus.ACCEPTED);
    }
}
