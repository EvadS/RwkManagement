package com.se.management.controller.base;

import com.se.management.model.search.SkillSearch;
import com.se.management.model.request.SkillRequest;
import com.se.management.model.response.SkillResponse;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Api(value = "Skill api management")
public interface SkillsControllerBase {

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
            @ApiResponse(code = 200, message = "Successfully retrieved paged skills list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @ApiOperation(value = "Search and paging",
            notes = "Filterable + sortable + pageable list", tags = {})
    @GetMapping("/search")
    ResponseEntity<Page<SkillResponse>> filter(@NotNull final SkillSearch request, Pageable pageable);


    @ApiOperation(value = "Skill details", notes = "Skill details by id", tags = {})
    @GetMapping(value = "/{id}")
    ResponseEntity<SkillResponse> getById(
            @ApiParam(value = "User unique identifier", required = true)
            @PathVariable(value = "id") Long id);

    @ApiOperation(value = "Create skill", nickname = "create",
            notes = "Create skill by id", tags = {})
    @PostMapping
    ResponseEntity<SkillResponse> create(@Valid @RequestBody
                                                  @ApiParam(value = "skill details for update", required = true)
                                                          SkillRequest skillRequest);

    @ApiOperation(value = "Update skill", nickname = "update",
            notes = "Update skill by id", tags = {})
    @PutMapping(value = "/{id}")
    ResponseEntity<SkillResponse> update(
            @ApiParam(value = "skill unique identifier", required = true)
            @PathVariable(value = "id") @NotNull Long id,
            @ApiParam(value = "skill details for update", required = true)
            @RequestBody SkillRequest request);

    @ApiOperation(value = "Delete skill", nickname = "delete",
            notes = "Update skill by id", tags = {})
    @DeleteMapping(value = "/{id}")
    ResponseEntity<SkillResponse> delete(
            @ApiParam(value = "skill unique identifier", required = true)
            @PathVariable(value = "id") @NotNull Long id);


}
