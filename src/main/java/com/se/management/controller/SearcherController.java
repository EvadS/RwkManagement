package com.se.management.controller;


import com.se.management.controller.base.SearcherControllerBase;
import com.se.management.model.request.SearcherRequest;
import com.se.management.model.response.SearcherInfoResponse;
import com.se.management.model.response.SearcherListItem;
import com.se.management.model.response.SearcherResponse;
import com.se.management.model.search.SkillSearch;
import com.se.management.service.SearcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/searcher")
@Validated
public class SearcherController implements SearcherControllerBase {

    private final Logger logger = LoggerFactory.getLogger(SearcherController.class);
    private final SearcherService searcherService;

    public SearcherController(SearcherService searcherService) {
        this.searcherService = searcherService;
    }

    @Override
    public ResponseEntity<Page<SearcherListItem>> list(@NotNull SkillSearch request, Pageable pageable) {
        logger.info("Handle list request to searcher");

        Page<SearcherListItem> searcherListItemPage = searcherService.search(pageable);
        return ResponseEntity.ok(searcherListItemPage);
    }

    @Override
    public ResponseEntity<SearcherInfoResponse> getById(Long id) {
        SearcherInfoResponse response = searcherService.get(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SearcherResponse> createSearcher(@Valid SearcherRequest searcherRequest) {
        SearcherResponse response = searcherService.create(searcherRequest);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SearcherResponse> updateSearcher(Long searcherId, @Valid SearcherRequest searcherRequest) {
        logger.debug("Handle update request to searcher : {}", searcherId);

        SearcherResponse response = searcherService.update(searcherId, searcherRequest);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity delete(@NotNull Long id) {
        logger.debug("Handle delete request to searcher : {}", id);

        searcherService.deleteSearcher(id);
        return ResponseEntity.accepted().build();
    }

}
