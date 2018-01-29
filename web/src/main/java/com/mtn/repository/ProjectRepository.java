package com.mtn.repository;

import com.mtn.model.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer>, JpaSpecificationExecutor<Project> {

    // TODO I don't think these will work as they are - they require an Interaction as a parameter. Need to rethink finding Projects
//    List<Project> findAllByInteractionsShoppingCenterIdAndDeletedDateIsNull(Integer shoppingCenterId);
//    List<Project> findAllByInteractionsShoppingCenterSurveyIdAndDeletedDateIsNull(Integer shoppingCenterSurveyId);
//    List<Project> findAllByInteractionsShoppingCenterCasingIdAndDeletedDateIsNull(Integer shoppingCenterCasingId);
//    List<Project> findAllByInteractionsStoreIdAndDeletedDateIsNull(Integer storeId);
//    List<Project> findAllByInteractionsStoreSurveyIdAndDeletedDateIsNull(Integer storeSurveyId);
//    List<Project> findAllByInteractionsStoreCasingIdAndDeletedDateIsNull(Integer storeCasingId);

    Project findOneByProjectName(String projectName);

}

