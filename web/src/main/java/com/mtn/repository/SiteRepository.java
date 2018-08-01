package com.mtn.repository;

import com.mtn.model.domain.Site;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SiteRepository extends JpaRepository<Site, Integer>, JpaSpecificationExecutor<Site> {

	Site findByIdAndDeletedDateIsNull(Integer locationId);

	Page<Site> findAllByDeletedDateIsNull(Pageable page);

	List<Site> findAllByIdIn(Integer[] ids);

	@Query(value = "Select si from Site si where st_within(si.location, :area) = true")
	List<Site> findWithinGeometry(@Param("area") Geometry area);

}
