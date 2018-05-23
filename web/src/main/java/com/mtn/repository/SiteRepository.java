package com.mtn.repository;

import com.mtn.model.domain.Site;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Allen on 4/24/2017.
 */
public interface SiteRepository extends JpaRepository<Site, Integer>, JpaSpecificationExecutor<Site> {

	Site findByIdAndDeletedDateIsNull(Integer locationId);

	Page<Site> findAllByDeletedDateIsNull(Pageable page);

	List<Site> findAllByIdIn(Integer[] ids);

//	@Query(value = "select l from Site l where within(l.location, :boundary) = true")
//	Page<Site> findLocationWithin(@Param("boundary") Geometry boundary, Pageable page);
}
