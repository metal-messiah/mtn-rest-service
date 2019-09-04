package com.mtn.repository;

import com.mtn.model.domain.SiteIsochrone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SiteIsochroneRepository extends JpaRepository<SiteIsochrone, Integer>, JpaSpecificationExecutor<SiteIsochrone> {

}
