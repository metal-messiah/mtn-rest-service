package com.mtn.repository;

import com.mtn.model.domain.Site;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SiteRepository extends EntityRepository<Site> {

	@Query(value = "Select si from Site si where st_within(si.location, :shape) = true and si.deletedDate is null")
	List<Site> findWithinGeometry(@Param("shape") Geometry shape);

	@Query(value = "Select si from Site si where st_within(si.location, ST_GeomFromGeoJson(:geoJson, 1, 4326)) = true and si.deletedDate is null")
	List<Site> findWithinGeoJson(@Param("geoJson") String geoJson);

}
