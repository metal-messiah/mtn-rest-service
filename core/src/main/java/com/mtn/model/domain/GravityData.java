package com.mtn.model.domain;

import javax.persistence.*;

@SqlResultSetMapping(
		name = "GravityData",
		classes = {
				@ConstructorResult(
						targetClass = com.mtn.model.domain.GravityData.class,
						columns = {
								@ColumnResult(name = "id"),
								@ColumnResult(name = "fips"),
								@ColumnResult(name = "siteId", type = Integer.class),
								@ColumnResult(name = "latitude", type = Float.class),
								@ColumnResult(name = "longitude", type = Float.class),
								@ColumnResult(name = "storeId", type = Integer.class),
								@ColumnResult(name = "driveSeconds", type = Integer.class),
								@ColumnResult(name = "areaSales", type = Integer.class),
								@ColumnResult(name = "storeFit"),
								@ColumnResult(name = "bannerId", type = Integer.class)
						}
				)
		}
)

@NamedNativeQueries({
		@NamedNativeQuery(
				name = "getGravityDataForProjectBg",
				query = "SELECT " +
						"UUID() AS id, " +
						"dt.fips, " +
						"si.site_id as siteId, " +
						"si.latitude as latitude, " +
						"si.longitude as longitude, " +
						"st.store_id as storeId, " +
						"dt.drive_seconds as driveSeconds, " +
						"st.area_sales as areaSales, " +
						"st.store_fit as storeFit, " +
						"st.banner_id as bannerId " +
						"FROM site_block_group_drive_time dt " +
						"JOIN site si ON si.site_id = dt.site_id " +
						"JOIN store st ON st.site_id = si.site_id " +
						"JOIN store_casing stc ON stc.store_id = st.store_id " +
						"JOIN store_casing_project scp ON scp.store_casing_id = stc.store_casing_id " +
						"WHERE scp.project_id = :projectId " +
						"AND dt.fips = :fips " +
						"AND st.area_sales IS NOT NULL " +
						"AND st.deleted_date IS NULL " +
						"AND stc.deleted_date IS NULL " +
						"ORDER BY dt.fips, drive_seconds",
				resultSetMapping = "GravityData",
				resultClass = com.mtn.model.domain.GravityData.class
		),
		@NamedNativeQuery(
				name = "getGravityDataForProject",
				query = "SELECT " +
						"UUID() AS id, " +
						"dt.fips, " +
						"si.site_id as siteId, " +
						"si.latitude as latitude, " +
						"si.longitude as longitude, " +
						"st.store_id as storeId, " +
						"dt.drive_seconds as driveSeconds, " +
						"st.area_sales as areaSales, " +
						"st.store_fit as storeFit, " +
						"st.banner_id as bannerId " +
						"FROM site_block_group_drive_time dt " +
						"JOIN site si ON si.site_id = dt.site_id " +
						"JOIN store st ON st.site_id = si.site_id " +
						"JOIN store_casing stc ON stc.store_id = st.store_id " +
						"JOIN store_casing_project scp ON scp.store_casing_id = stc.store_casing_id " +
						"WHERE scp.project_id = :projectId " +
						"AND st.area_sales IS NOT NULL " +
						"AND st.deleted_date IS NULL " +
						"AND stc.deleted_date IS NULL " +
						"ORDER BY dt.fips, drive_seconds",
				resultSetMapping = "GravityData",
				resultClass = com.mtn.model.domain.GravityData.class
		),
		@NamedNativeQuery(
				name = "getGravityDataForProjectSite",
				query = "SELECT " +
						"UUID() AS id, " +
						"dt.fips, " +
						"si.site_id as siteId, " +
						"si.latitude as latitude, " +
						"si.longitude as longitude, " +
						"st.store_id as storeId, " +
						"dt.drive_seconds as driveSeconds, " +
						"st.area_sales as areaSales, " +
						"st.store_fit as storeFit, " +
						"st.banner_id as bannerId " +
						"FROM site_block_group_drive_time dt " +
						"JOIN site si ON si.site_id = dt.site_id " +
						"JOIN store st ON st.site_id = si.site_id " +
						"JOIN store_casing stc ON stc.store_id = st.store_id " +
						"JOIN store_casing_project scp ON scp.store_casing_id = stc.store_casing_id " +
						"WHERE scp.project_id = :projectId " +
						"AND st.store_id = :storeId " +
						"AND st.area_sales IS NOT NULL " +
						"AND st.deleted_date IS NULL " +
						"AND stc.deleted_date IS NULL " +
						"ORDER BY dt.fips, drive_seconds",
				resultSetMapping = "GravityData",
				resultClass = com.mtn.model.domain.GravityData.class
		)
})


@Entity
@Table
public class GravityData {

	@Id
	private String id;
	private String fips;
	private Integer siteId;
	private Float latitude;
	private Float longitude;
	private Integer storeId;
	private Integer driveSeconds;
	private Integer areaSales;
	private String storeFit;
	private Integer bannerid;

	public GravityData() {

	}

	public GravityData(String id, String fips, Integer siteId, Float latitude, Float longitude, Integer storeId, Integer driveSeconds, Integer areaSales, String storeFit, Integer bannerid) {
		this.id = id;
		this.fips = fips;
		this.siteId = siteId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.storeId = storeId;
		this.driveSeconds = driveSeconds;
		this.areaSales = areaSales;
		this.storeFit = storeFit;
		this.bannerid = bannerid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFips() {
		return fips;
	}

	public void setFips(String fips) {
		this.fips = fips;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Integer getDriveSeconds() {
		return driveSeconds;
	}

	public void setDriveSeconds(Integer driveSeconds) {
		this.driveSeconds = driveSeconds;
	}

	public Integer getAreaSales() {
		return areaSales;
	}

	public void setAreaSales(Integer areaSales) {
		this.areaSales = areaSales;
	}

	public String getStoreFit() {
		return storeFit;
	}

	public void setStoreFit(String storeFit) {
		this.storeFit = storeFit;
	}

	public Integer getBannerid() {
		return bannerid;
	}

	public void setBannerid(Integer bannerid) {
		this.bannerid = bannerid;
	}
}
