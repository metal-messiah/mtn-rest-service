package com.mtn.repository;

import com.mtn.model.domain.search.StoreSearchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Allen on 4/26/2017.
 */
public interface StoreSearchRepository extends JpaRepository<StoreSearchResult, Integer>, JpaSpecificationExecutor<StoreSearchResult> {
}
