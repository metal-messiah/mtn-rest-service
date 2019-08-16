package com.mtn.repository;

import com.mtn.model.domain.ActiveAndFutureStores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ActiveAndFutureStoresRepository extends JpaRepository<ActiveAndFutureStores, Integer>, JpaSpecificationExecutor<ActiveAndFutureStores> {

}
