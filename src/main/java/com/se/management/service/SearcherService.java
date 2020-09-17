package com.se.management.service;

import com.se.management.model.request.SearcherRequest;
import com.se.management.model.response.SearcherInfoResponse;
import com.se.management.model.response.SearcherListItem;
import com.se.management.model.response.SearcherResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearcherService {
    SearcherResponse create(SearcherRequest searcher);

    SearcherResponse update(Long searcherId, SearcherRequest searcherRequest);

    boolean deleteSearcher(long id);

    Page<SearcherListItem> search(Pageable pageable);

    SearcherInfoResponse get(Long id);
}
