package com.mtn.model.converter;

import com.mtn.model.domain.search.StoreSearchResult;
import com.mtn.model.view.search.SearchResultView;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Allen on 4/26/2017.
 */
public class StoreSearchResultToSearchResultViewConverter implements Converter<StoreSearchResult, SearchResultView> {

    @Override
    public SearchResultView convert(StoreSearchResult storeSearchResult) {
        return StoreSearchResultToSearchResultViewConverter.build(storeSearchResult);
    }

    public static SearchResultView build(StoreSearchResult storeSearchResult) {
        return new SearchResultView(storeSearchResult);
    }
}
