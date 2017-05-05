package com.mtn.controller;

import com.mtn.constant.SearchType;
import com.mtn.model.view.search.SearchRequest;
import com.mtn.model.view.search.SearchResultView;
import com.mtn.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Allen on 4/26/2017.
 */
@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity search(@RequestBody SearchRequest request) {
        validateSearchRequest(request);

        List<SearchResultView> searchResults = searchService.search(request);
        return ResponseEntity.ok(searchResults);
    }

    private void validateSearchRequest(SearchRequest searchRequest) {
        if (searchRequest.getSearchType() == null) {
            throw new IllegalArgumentException(String.format("searchType must be one of: %s", StringUtils.join(SearchType.values(), ", ")));
        } else if (searchRequest.getDistance() != null && searchRequest.getDistance() <= 0) {
            throw new IllegalArgumentException("distance must be greater than 0");
        }
    }
}
