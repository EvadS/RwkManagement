package com.se.management.controller.base;

import com.se.management.model.request.SearcherRequest;
import com.se.management.model.response.SearcherInfoResponse;
import com.se.management.model.response.SearcherListItem;
import com.se.management.model.response.SearcherResponse;
import com.se.management.model.response.SkillResponse;
import com.se.management.model.search.SkillSearch;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Api(value = "Searcher api management.")
public interface SearcherControllerBase {

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.", defaultValue = "5"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")
    })

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved paged searchers list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })


    @ApiOperation(value = "Search and paging",
            notes = "Filterable + sortable + pageable list", tags = {})
    @GetMapping("/search")
    ResponseEntity<Page<SearcherListItem>> list(@NotNull final SkillSearch request, Pageable pageable);

    @ApiOperation(value = "Searcher details", notes = "Searcher details by id", tags = {})
    @GetMapping(value = "/{id}")
    ResponseEntity<SearcherInfoResponse> getById(
            @ApiParam(value = "Searcher unique identifier", required = true)
            @PathVariable(value = "id") Long id);


    @ApiOperation(value = "Create searcher", nickname = "create",
            notes = "Create and save new searcher", tags = {})
    @PostMapping
    ResponseEntity<SearcherResponse> createSearcher(
            @Valid @RequestBody
            @ApiParam(value = "Information about new searcher", required = true)
                    SearcherRequest searcherRequest);


    @ApiOperation(value = "Update searcher", nickname = "update",
            notes = "Update searchers information by id", tags = {})
    @PutMapping(value = "/{id}")
    ResponseEntity<SearcherResponse> updateSearcher(
            @ApiParam(value = "searcher unique identifier", required = true)
            @PathVariable(value = "id") Long searcherId,
            @Valid @RequestBody
            @ApiParam(value = "Information about searcher", required = true)
                    SearcherRequest searcherRequest);


    @ApiOperation(value = "Delete searcher", nickname = "delete",
            notes = "Delete searcher by id", tags = {})
    @DeleteMapping(value = "/{id}")
    ResponseEntity<SkillResponse> delete(
            @ApiParam(value = "Searcher unique identifier", required = true)
            @PathVariable(value = "id") @NotNull Long id);

}
