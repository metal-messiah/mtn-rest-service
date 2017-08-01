package com.mtn.repository;

import com.mtn.model.domain.StoreSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Allen on 6/7/2017.
 */
public interface StoreSurveyRepository extends JpaRepository<StoreSurvey, Integer>, JpaSpecificationExecutor<StoreSurvey> {

    List<StoreSurvey> findAllByStoreId(Integer storeId);

    List<StoreSurvey> findAllByInteractionsProjectIdAndDeletedDateIsNull(Integer id);

}
