package com.mtn.model.converter;

import com.mtn.model.domain.search.ShoppingCenterSearchResult;
import com.mtn.model.view.search.SearchResultView;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Allen on 4/26/2017.
 */
public class ShoppingCenterSearchResultToSearchResultViewConverter implements Converter<ShoppingCenterSearchResult, SearchResultView> {

    @Override
    public SearchResultView convert(ShoppingCenterSearchResult shoppingCenterSearchResult) {
        return new SearchResultView(shoppingCenterSearchResult);
    }

    public static SearchResultView build(ShoppingCenterSearchResult shoppingCenterSearchResult) {
        return new ShoppingCenterSearchResultToSearchResultViewConverter().convert(shoppingCenterSearchResult);
    }
}
