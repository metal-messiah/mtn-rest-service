package com.mtn.controller;

import com.mtn.model.view.search.SearchRequest;
import com.mtn.model.view.search.SearchResultView;
import com.mtn.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Allen on 4/26/2017.
 */
@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity search(@RequestBody SearchRequest request, Pageable page) {
        Page<SearchResultView> searchResults = searchService.search(request, page);
        return ResponseEntity.ok(searchResults);
    }
}
