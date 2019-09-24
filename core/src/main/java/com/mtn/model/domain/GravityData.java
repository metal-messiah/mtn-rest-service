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
								@ColumnResult(name = "driveSeconds", type = Float.class),
								@ColumnResult(name = "areaSales", type = Float.class),
								@ColumnResult(name = "storeFit"),
								@ColumnResult(name = "bannerId", type = Integer.class),
								@ColumnResult(name = "fitScore", type = Float.class)
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
						"st.banner_id as bannerId," +
						"IFNULL(f.fit_score, 100) as fitScore " +
						"FROM site_block_group_drive_time dt " +
						"JOIN site si ON si.site_id = dt.site_id " +
						"JOIN store st ON st.site_id = si.site_id " +
						"JOIN store_casing stc ON stc.store_id = st.store_id " +
						"JOIN store_casing_project scp ON scp.store_casing_id = stc.store_casing_id " +
						"LEFT JOIN fit_reference fr on fr.fit_category = st.store_fit " +
						"LEFT JOIN fit_scores f ON f.fips = dt.fips and fr.fit_id = f.fit_id " +
						"WHERE scp.project_id = :projectId " +
						"AND dt.fips = :fips " +
						"AND st.is_float = 0 " +
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
						"st.banner_id as bannerId," +
						"IFNULL(f.fit_score, 100) as fitScore " +
						"FROM site_block_group_drive_time dt " +
						"JOIN site si ON si.site_id = dt.site_id " +
						"JOIN store st ON st.site_id = si.site_id " +
						"JOIN store_casing stc ON stc.store_id = st.store_id " +
						"JOIN store_casing_project scp ON scp.store_casing_id = stc.store_casing_id " +
						"LEFT JOIN fit_reference fr on fr.fit_category = st.store_fit " +
						"LEFT JOIN fit_scores f ON f.fips = dt.fips and fr.fit_id = f.fit_id " +
						"WHERE scp.project_id = :projectId " +
						"AND st.is_float = 0 " +
						"AND st.area_sales IS NOT NULL " +
						"AND st.deleted_date IS NULL " +
						"AND stc.deleted_date IS NULL " +
//						"and (st.store_fit is null || st.store_fit != 'Club') " +
//						"and (st.banner_id is null || st.banner_id != 373) " +
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
	private Float driveSeconds;
	private Float areaSales;
	private String storeFit;
	private Integer bannerId;
	private Float fitScore;

	public GravityData() {

	}

	public GravityData(String id, String fips, Integer siteId, Float latitude, Float longitude, Integer storeId, Float driveSeconds, Float areaSales, String storeFit, Integer bannerId, Float fitScore) {
		this.id = id;
		this.fips = fips;
		this.siteId = siteId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.storeId = storeId;
		this.driveSeconds = driveSeconds;
		this.areaSales = areaSales;
		this.storeFit = storeFit;
		this.bannerId = bannerId;
		this.fitScore = fitScore;
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

	public Float getDriveSeconds() {
		return driveSeconds;
	}

	public void setDriveSeconds(Float driveSeconds) {
		this.driveSeconds = driveSeconds;
	}

	public Float getAreaSales() {
		return areaSales;
	}

	public void setAreaSales(Float areaSales) {
		this.areaSales = areaSales;
	}

	public String getStoreFit() {
		return storeFit;
	}

	public void setStoreFit(String storeFit) {
		this.storeFit = storeFit;
	}

	public Integer getBannerId() {
		return bannerId;
	}

	public void setBannerId(Integer bannerId) {
		this.bannerId = bannerId;
	}

	public Float getFitScore() {
		return fitScore;
	}

	public void setFitScore(Float fitScore) {
		this.fitScore = fitScore;
	}
}
