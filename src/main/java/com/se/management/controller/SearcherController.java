package com.se.management.controller;


import com.se.management.model.request.SearcherRequest;
import com.se.management.model.response.SearcherListItem;
import com.se.management.model.response.SearcherResponse;
import com.se.management.service.SearcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
@RequestMapping("/searcher")
@Validated
public class SearcherController {

    private final Logger logger = LoggerFactory.getLogger(SearcherController.class);
    private final SearcherService searcherService;

    public SearcherController(SearcherService searcherService) {
        this.searcherService = searcherService;
    }

    @PostMapping()
    public ResponseEntity<SearcherResponse> createSearcher(@Valid @RequestBody  SearcherRequest searcherRequest) {
        logger.debug("Handle create request ");

        SearcherResponse response = searcherService.create(searcherRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<SearcherResponse> updateSearcher(@PathVariable(value = "id") Long searcherId,
                                                              @Valid @RequestBody SearcherRequest searcherRequest) {
        logger.debug("Handle update request to searcher : {}", searcherId);

        SearcherResponse response = searcherService.update(searcherId, searcherRequest);
        return ResponseEntity.ok(response);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity searcherDelete(@PathVariable(value = "id") long id) {
        logger.debug("Handle delete request to searcher : {}", id);

        searcherService.deleteSearcher(id);
        return ResponseEntity.ok("Done.");
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Page<SearcherListItem>> list(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                       @RequestParam(required = false, defaultValue = "40") Integer limit,
                                                       @RequestParam(required = false, defaultValue = "") String filter) {
        logger.info("Handle list request to searcher");
        Pageable pageable = PageRequest.of(page, limit);

        Page<SearcherListItem> searcherListItemPage = searcherService.search(pageable);
        return ResponseEntity.ok(searcherListItemPage);
    }

    // TODO: temporary
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    String handleConstraintViolationException(ConstraintViolationException e) {
        return "not valid due to validation error: " + e.getMessage();
    }

}
