package com.mtn.service;

import com.mtn.constant.SearchType;
import com.mtn.model.converter.ShoppingCenterSearchResultToSearchResultViewConverter;
import com.mtn.model.domain.search.ShoppingCenterSearchResult;
import com.mtn.model.view.search.SearchRequest;
import com.mtn.model.view.search.SearchResultView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 4/26/2017.
 */
@Service
public class SearchService {

    @Autowired
    private ShoppingCenterSearchService shoppingCenterSearchService;

    public List<SearchResultView> search(SearchRequest request) {
        switch (request.getSearchType()) {
            case SHOPPING_CENTER:
                return searchShoppingCenters(request);
            case STORE:
                return searchStores(request);
            default:
                throw new IllegalArgumentException(String.format("searchType must be one of: %s", StringUtils.join(SearchType.values(), ", ")));
        }
    }

    private List<SearchResultView> searchShoppingCenters(SearchRequest request) {
        List<ShoppingCenterSearchResult> results = shoppingCenterSearchService.search(request);
        return results.stream().map(ShoppingCenterSearchResultToSearchResultViewConverter::build).collect(Collectors.toList());
    }

    private List<SearchResultView> searchStores(SearchRequest request) {
        throw new UnsupportedOperationException();
    }
}
