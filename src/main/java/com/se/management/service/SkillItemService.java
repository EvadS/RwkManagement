package com.se.management.service;

import com.se.management.model.SkillSearch;
import com.se.management.model.request.SkillItemRequest;
import com.se.management.model.request.SkillRequest;
import com.se.management.model.response.SkillItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SkillItemService {
    SkillItemResponse getById(Long skillId);

    SkillItemResponse create(SkillItemRequest skillRequest);

    SkillItemResponse update(Long skillId, SkillItemRequest skillRequest);

    boolean delete(Long skillId);

    Page<SkillItemResponse> filter(SkillSearch request, Pageable pageable);
}
