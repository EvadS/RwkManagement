package com.se.management.mapper;

import com.se.management.domain.Searcher;
import com.se.management.model.request.SearcherRequest;
import com.se.management.model.response.SearcherResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Date;

@Mapper
public interface SearcherMapper {
    SearcherMapper INSTANCE = Mappers.getMapper(SearcherMapper.class);

    Searcher SearcherRequestToSearcher(SearcherRequest searcherRequest);

    SearcherResponse SearcherToSearcherResponse(Searcher searcher);

    default long map(java.util.Date date) {
        return date == null ? 0 : date.getTime();
    }

    default Date fromInstant(long  val) {
        return  new Date(val);
    }
}
