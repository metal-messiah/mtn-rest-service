package com.mtn.repository;

import com.mtn.model.domain.Site;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SiteRepository extends EntityRepository<Site> {

	@Query(value = "Select si from Site si where st_within(si.location, :area) = true")
	List<Site> findWithinGeometry(@Param("area") Geometry area);

}
