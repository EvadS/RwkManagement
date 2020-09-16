package com.se.management.controller.base;

import com.se.management.model.request.MessengerTypeRequest;
import com.se.management.model.response.MessengerTypeResponse;
import com.se.management.model.search.MessengerTypeSearch;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Api(value = "Messengers api management.")
public interface MessengerTypeControllerBase {

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
            @ApiResponse(code = 200, message = "Successfully retrieved paged messengers list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @ApiOperation(value = "Search and paging",
            notes = "Filterable + sortable + pageable list", tags = {})
    @GetMapping("/search")
    ResponseEntity<Page<MessengerTypeResponse>> filter(@NotNull final MessengerTypeSearch request, Pageable pageable);


    @ApiOperation(value = "Messenger details", notes = "Messenger details by id", tags = {})
    @GetMapping(value = "/{id}")
    ResponseEntity<MessengerTypeResponse> getByIdd(
            @ApiParam(value = "Messenger unique identifier", required = true)
            @PathVariable(value = "id") Long id);

    @ApiOperation(value = "Create messenger", nickname = "create",
            notes = "Create new messenger type", tags = {})
    @PostMapping
    ResponseEntity<MessengerTypeResponse> create(@Valid @RequestBody
                                                 @ApiParam(value = "Info for new messenger", required = true)
                                                         MessengerTypeRequest request);

    @ApiOperation(value = "Update messenger", nickname = "update",
            notes = "Update messenger by id", tags = {})
    @PutMapping(value = "/{id}")
    ResponseEntity<MessengerTypeResponse> update(
            @ApiParam(value = "Messenger unique identifier", required = true)
            @PathVariable(value = "id") @NotNull Long id,
            @ApiParam(value = "Messenger details for update", required = true)
            @RequestBody MessengerTypeRequest request);

    @ApiOperation(value = "Delete messenger type.", nickname = "delete",
            notes = "Delete messenger type.", tags = {})
    @DeleteMapping(value = "/{id}")
    ResponseEntity delete(
            @ApiParam(value = "Messenger unique identifier", required = true)
            @PathVariable(value = "id") @NotNull Long id);


}
