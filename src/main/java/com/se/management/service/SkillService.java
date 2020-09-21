package com.se.management.service;

import com.se.management.model.search.SkillSearch;
import com.se.management.model.request.SkillRequest;
import com.se.management.model.response.SkillResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SkillService {
    SkillResponse getById(Long skillId);

    SkillResponse create(SkillRequest skillRequest);

    SkillResponse update(Long skillId, SkillRequest skillRequest);

    boolean delete(Long skillId);

    Page<SkillResponse> filter(SkillSearch request, Pageable pageable);

    List<SkillResponse> list();
}
