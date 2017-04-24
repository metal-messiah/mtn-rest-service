package com.mtn.repository;

import com.mtn.model.domain.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Allen on 4/24/2017.
 */
public interface SiteRepository extends JpaRepository<Site, Integer>, JpaSpecificationExecutor<Site> {

}
