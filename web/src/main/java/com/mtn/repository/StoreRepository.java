package com.mtn.repository;

import com.mtn.model.domain.Store;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends EntityRepository<Store> {

	@Query("SELECT st FROM Store st where ST_Within(st.site.location, ST_GeomFromGeoJson(:geoJson, 1, 4326)) = true and st.deletedDate is null")
	List<Store> findAllInGeoJson(@Param("geoJson") String geoJson);

}
