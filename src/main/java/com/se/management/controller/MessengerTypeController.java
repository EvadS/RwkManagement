package com.se.management.controller;

import com.se.management.controller.base.MessengerTypeControllerBase;
import com.se.management.model.request.MessengerTypeRequest;
import com.se.management.model.response.MessengerTypeResponse;
import com.se.management.model.response.SkillResponse;
import com.se.management.model.search.MessengerTypeSearch;
import com.se.management.service.MessengerTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping("/messenger")
public class MessengerTypeController implements MessengerTypeControllerBase {

    private final MessengerTypeService messengerTypeService;

    public MessengerTypeController(MessengerTypeService messengerTypeService) {
        this.messengerTypeService = messengerTypeService;
    }

    @Override
    public ResponseEntity<Page<MessengerTypeResponse>> filter(@NotNull MessengerTypeSearch request, Pageable pageable) {
        Page<MessengerTypeResponse> responsePage = messengerTypeService.filter(request, pageable);
        return ResponseEntity.ok(responsePage);
    }

    @Override
    public ResponseEntity<List<MessengerTypeResponse>> getAll() {

        List<MessengerTypeResponse> messengerTypeResponseList = messengerTypeService.list();
        return ResponseEntity.ok(messengerTypeResponseList);
    }

    @Override
    public ResponseEntity<MessengerTypeResponse> getByIdd(Long id) {
        MessengerTypeResponse messengerTypeResponse = messengerTypeService.getById(id);
        return ResponseEntity.ok(messengerTypeResponse);

    }

    @Override
    public ResponseEntity<MessengerTypeResponse> create(@Valid MessengerTypeRequest request) {
        MessengerTypeResponse response = messengerTypeService.create(request);
        return new ResponseEntity(response, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<MessengerTypeResponse> update(@NotNull Long id, MessengerTypeRequest request) {
        MessengerTypeResponse messengerTypeResponse = messengerTypeService.update(id, request);
        return new ResponseEntity<>(messengerTypeResponse, HttpStatus.ACCEPTED);

    }

    @Override
    public ResponseEntity delete(@NotNull Long id) {
        messengerTypeService.delete(id);
        return new ResponseEntity(HttpStatus.ACCEPTED);

    }
}
