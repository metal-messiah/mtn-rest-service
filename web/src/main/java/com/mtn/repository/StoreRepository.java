package com.mtn.repository;

import com.mtn.constant.StoreType;
import com.mtn.model.domain.Store;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends EntityRepository<Store> {

	@Query("SELECT st FROM Store st " +
			"where ST_Within(st.site.location, ST_GeomFromGeoJson(:geoJson, 1, 4326)) = true " +
			"and (:geometry is null or ST_Within(st.site.location, :geometry) = true) " +
			"and st.storeType in :storeTypes " +
			"and st.deletedDate is null")
	List<Store> findAllInGeoJson(@Param("geoJson") String geoJson,
								 @Param("geometry") Geometry geometry,
								 @Param("storeTypes") List<StoreType> storeTypes);

	@Query("SELECT st FROM Store st " +
			"where ST_Distance_Sphere(st.site.location, ST_PointFromText(concat('Point(', :longitude, ' ', :latitude, ')'), 4326)) <= :radiusMeters " +
			"and (:geometry is null or ST_Within(st.site.location, :geometry) = true) " +
			"and st.storeType in :storeTypes " +
			"and st.deletedDate is null")
	List<Store> findAllInRadius(@Param("latitude") Float latitude,
								@Param("longitude") Float longitude,
								@Param("radiusMeters") Float radiusMeters,
								@Param("geometry") Geometry geometry,
								@Param("storeTypes") List<StoreType> storeTypes);

	@Query("SELECT st FROM Store st " +
			"where (:geometry is null or ST_Within(st.site.location, :geometry) = true) " +
			"and st.site.latitude <= :north " +
			"and st.site.latitude >= :south " +
			"and st.site.longitude <= :east " +
			"and st.site.longitude >= :west " +
			"and st.storeType in :storeTypes " +
			"and st.deletedDate is null")
	List<Store> findAllInGeometry(@Param("geometry") Geometry geometry,
								  @Param("north") Float north,
								  @Param("south") Float south,
								  @Param("east") Float east,
								  @Param("west") Float west,
								  @Param("storeTypes") List<StoreType> storeTypes);
}
