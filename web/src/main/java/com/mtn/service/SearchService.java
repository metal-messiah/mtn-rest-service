package com.mtn.service;

import com.mtn.constant.SearchType;
import com.mtn.model.converter.ShoppingCenterSearchResultToSearchResultViewConverter;
import com.mtn.model.converter.StoreSearchResultToSearchResultViewConverter;
import com.mtn.model.domain.search.ShoppingCenterSearchResult;
import com.mtn.model.domain.search.StoreSearchResult;
import com.mtn.model.view.search.SearchRequest;
import com.mtn.model.view.search.SearchResultView;
import com.mtn.repository.ShoppingCenterSearchRepository;
import com.mtn.repository.StoreSearchRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import static com.mtn.repository.specification.ShoppingCenterSearchSpecifications.*;
import static com.mtn.repository.specification.StoreSearchSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 4/26/2017.
 */
@Service
public class SearchService {

    @Autowired
    private ShoppingCenterSearchRepository shoppingCenterSearchRepository;

    @Autowired
    private StoreSearchRepository storeSearchRepository;

    public Page<SearchResultView> search(SearchRequest request, Pageable page) {
        switch (request.getSearchType()) {
            case SHOPPING_CENTER:
                return searchShoppingCenters(request, page);
            case STORE:
                return searchStores(request, page);
            default:
                throw new IllegalArgumentException(String.format("searchType must be one of: %s", StringUtils.join(SearchType.values(), ", ")));
        }
    }

    private Page<SearchResultView> searchShoppingCenters(SearchRequest request, Pageable page) {
        Specification<ShoppingCenterSearchResult> specification = generateShoppingCenterSpecifications(request);
        Page<ShoppingCenterSearchResult> searchResults = shoppingCenterSearchRepository.findAll(specification, page);
        return searchResults.map(new ShoppingCenterSearchResultToSearchResultViewConverter());
    }

    private Page<SearchResultView> searchStores(SearchRequest request, Pageable page) {
        Specification<StoreSearchResult> specification = generateStoreSpecifications(request);
        Page<StoreSearchResult> searchResults = storeSearchRepository.findAll(specification, page);
        return searchResults.map(new StoreSearchResultToSearchResultViewConverter());
    }

    private Specification<ShoppingCenterSearchResult> generateShoppingCenterSpecifications(SearchRequest request) {
        Specifications<ShoppingCenterSearchResult> specifications = where(shoppingCenterIsNotDeleted());

        if (StringUtils.isNotBlank(request.getName())) {
            specifications = specifications.and(shoppingCenterNameContains(request.getName()));
        }
        if (StringUtils.isNotBlank(request.getOwner())) {
            specifications = specifications.and(shoppingCenterOwnerContains(request.getOwner()));
        }
        if (StringUtils.isNotBlank(request.getPostalCode())) {
            specifications = specifications.and(shoppingCenterPostalCodeContains(request.getPostalCode()));
        }
        if (StringUtils.isNotBlank(request.getCity())) {
            specifications = specifications.and(shoppingCenterCityContains(request.getCity()));
        }
        if (StringUtils.isNotBlank(request.getCounty())) {
            specifications = specifications.and(shoppingCenterCountyContains(request.getCounty()));
        }
        if (StringUtils.isNotBlank(request.getState())) {
            specifications = specifications.and(shoppingCenterStateContains(request.getState()));
        }

        return specifications;
    }

    private Specification<StoreSearchResult> generateStoreSpecifications(SearchRequest request) {
        Specifications<StoreSearchResult> specifications = where(storeIsNotDeleted());

        if (StringUtils.isNotBlank(request.getName())) {
            specifications = specifications.and(storeNameContains(request.getName()));
        }
        if (StringUtils.isNotBlank(request.getPostalCode())) {
            specifications = specifications.and(storePostalCodeContains(request.getPostalCode()));
        }
        if (StringUtils.isNotBlank(request.getCity())) {
            specifications = specifications.and(storeCityContains(request.getCity()));
        }
        if (StringUtils.isNotBlank(request.getCounty())) {
            specifications = specifications.and(storeCountyContains(request.getCounty()));
        }
        if (StringUtils.isNotBlank(request.getState())) {
            specifications = specifications.and(storeStateContains(request.getState()));
        }

        return specifications;
    }
}
