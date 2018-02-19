package com.mtn.repository;

import com.mtn.model.domain.Site;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Allen on 4/24/2017.
 */
public interface SiteRepository extends JpaRepository<Site, Integer>, JpaSpecificationExecutor<Site> {

	Site findByIdAndDeletedDateIsNull(Integer locationId);

	Page<Site> findAllByIdLessThanAndDeletedDateIsNull(Integer maxId, Pageable page);

	@Query(value = "select l from Site l where within(l.location, :boundary) = true")
	Page<Site> findLocationWithin(@Param("boundary") Geometry boundary, Pageable page);
}
