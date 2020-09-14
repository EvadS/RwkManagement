package com.se.management.controller;

import com.se.management.model.request.SearcherRequest;
import com.se.management.model.response.SearcherListItem;
import com.se.management.model.response.SearcherResponse;
import com.se.management.service.SearcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/searcher")
public class SearcherController {

    private final Logger logger = LoggerFactory.getLogger(SearcherController.class);

    @Autowired
    private SearcherService searcherService;

    @PostMapping()
    public ResponseEntity<SearcherResponse> createSearcher(@RequestBody SearcherRequest searcherRequest) {

        logger.debug("Handle create request ");

        SearcherResponse response = searcherService.create(searcherRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<SearcherResponse> updatePromoBanner(@PathVariable(value = "id") Long searcherId,
                                            @Valid @RequestBody SearcherRequest searcherRequest) {
        logger.debug("Handle update request to searcher : {}", searcherId);

        SearcherResponse response = searcherService.update(searcherId,searcherRequest);
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
}
