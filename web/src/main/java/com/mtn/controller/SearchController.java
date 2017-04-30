package com.mtn.controller;

import com.mtn.model.view.search.SearchRequest;
import com.mtn.model.view.search.SearchResultView;
import com.mtn.service.SearchService;
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
        List<SearchResultView> searchResults = searchService.search(request);
        return ResponseEntity.ok(searchResults);
    }
}
