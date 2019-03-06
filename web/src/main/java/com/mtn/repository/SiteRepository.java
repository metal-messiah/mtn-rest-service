package com.mtn.repository;

import com.mtn.model.domain.Site;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SiteRepository extends EntityRepository<Site> {

	@Query(value = "Select si from Site si " +
			"where st_within(si.location, :shape) = true " +
			"and si.deletedDate is null " +
			"and (:restriction is null or ST_Within(si.location, :restriction) = true)")
	List<Site> findWithinGeometry(@Param("shape") Geometry shape, @Param("restriction") Geometry restriction);

	@Query("SELECT si FROM Site si " +
			"where ST_Distance_Sphere(si.location, ST_PointFromText(concat('Point(', :longitude, ' ', :latitude, ')'), 4326)) <= :radiusMeters " +
			"and (:geometry is null or ST_Within(si.location, :geometry) = true) " +
			"and si.deletedDate is null")
	Page<Site> findAllInRadius(Pageable page,
							   @Param("latitude") Float latitude,
							   @Param("longitude") Float longitude,
							   @Param("radiusMeters") Float radiusMeters,
							   @Param("geometry") Geometry geometry);

	@Query(value = "Select si from Site si " +
			"where st_within(si.location, ST_GeomFromGeoJson(:geoJson, 1, 4326)) = true " +
			"and si.deletedDate is null " +
			"and (:restriction is null or ST_Within(si.location, :restriction) = true)")
	List<Site> findWithinGeoJson(@Param("geoJson") String geoJson, @Param("restriction") Geometry restriction);

	@Query(value = "Select si from Site si " +
			"where st_within(si.location, ST_GeomFromGeoJson(:geoJson, 1, 4326)) = true " +
			"and si.deletedDate is null " +
			"and (:restriction is null or ST_Within(si.location, :restriction) = true)")
	Page<Site> findWithinGeoJson(Pageable page, @Param("geoJson") String geoJson, @Param("restriction") Geometry restriction);

	@Query(value = "Select si from Site si " +
			"where (:restriction is null or ST_Within(si.location, :restriction) = true) " +
			"and si.latitude <= :north " +
			"and si.latitude >= :south " +
			"and si.longitude <= :east " +
			"and si.longitude >= :west " +
			"and si.deletedDate is null")
	List<Site> findAllInBounds(@Param("restriction") Geometry restriction,
							   @Param("north") Float north,
							   @Param("south") Float south,
							   @Param("east") Float east,
							   @Param("west") Float west);

	@Query(value = "Select si from Site si " +
			"where (:restriction is null or ST_Within(si.location, :restriction) = true) " +
			"and si.latitude <= :north " +
			"and si.latitude >= :south " +
			"and si.longitude <= :east " +
			"and si.longitude >= :west " +
			"and si.deletedDate is null")
	Page<Site> findAllInBounds(Pageable page,
							   @Param("restriction") Geometry restriction,
							   @Param("north") Float north,
							   @Param("south") Float south,
							   @Param("east") Float east,
							   @Param("west") Float west);

	@Query(value = "Select si from Site si " +
			"where (:restriction is null or ST_Within(si.location, :restriction) = true) " +
			"and si.stores IS EMPTY " +
			"and si.latitude <= :north " +
			"and si.latitude >= :south " +
			"and si.longitude <= :east " +
			"and si.longitude >= :west " +
			"and si.deletedDate is null")
	List<Site> findAllInBoundsWithoutStores(@Param("restriction") Geometry restriction,
											@Param("north") Float north,
											@Param("south") Float south,
											@Param("east") Float east,
											@Param("west") Float west);

}
