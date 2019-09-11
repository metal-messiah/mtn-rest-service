package com.mtn.repository;

import com.mtn.model.domain.BlockGroupPopulationCentroid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlockGroupPopulationCentroidRepository extends JpaRepository<BlockGroupPopulationCentroid, Integer>, JpaSpecificationExecutor<BlockGroupPopulationCentroid> {

	BlockGroupPopulationCentroid findByFipsEquals(String fips);

	@Query(value = "SELECT c.* FROM block_group_population_centroid c JOIN site_block_group sb ON sb.fips = c.fips WHERE sb.site_id = ?1", nativeQuery = true)
	List<BlockGroupPopulationCentroid> findAllForSite(Integer siteId);

	@Query(value = "SELECT DISTINCT c.* FROM block_group_population_centroid c JOIN site_block_group sb ON sb.fips = c.fips JOIN store st ON st.site_id = sb.site_id WHERE st.store_id IN ?1", nativeQuery = true)
	List<BlockGroupPopulationCentroid> findAllForStores(List<Integer> storeIds);

	@Query(value = "SELECT DISTINCT c.* " +
			"FROM block_group_population_centroid c " +
			"JOIN site_block_group sb ON sb.fips = c.fips " +
			"JOIN store st ON st.site_id = sb.site_id " +
			"join store_casing sc on st.store_id = sc.store_id " +
			"join store_casing_project scp on scp.store_casing_id = sc.store_casing_id " +
			"where scp.project_id = :projectId " +
			"and sc.deleted_date is null " +
			"and st.deleted_date is null", nativeQuery = true)
	List<BlockGroupPopulationCentroid> findAllForProject(@Param("projectId") Integer projectId);

}
