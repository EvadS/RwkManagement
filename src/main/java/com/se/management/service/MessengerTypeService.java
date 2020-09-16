package com.se.management.service;

import com.se.management.model.request.MessengerTypeRequest;
import com.se.management.model.response.MessengerTypeResponse;
import com.se.management.model.response.SkillResponse;
import com.se.management.model.search.MessengerTypeSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessengerTypeService {

    Page<MessengerTypeResponse> filter(MessengerTypeSearch request, Pageable pageable);

    MessengerTypeResponse getById(Long id);

    MessengerTypeResponse create(MessengerTypeRequest request);

    MessengerTypeResponse update(Long id, MessengerTypeRequest request);

    void delete(Long id);
}
