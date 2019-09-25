package com.mtn.model.domain;

import javax.persistence.*;

@SqlResultSetMapping(
		name = "StoreGravityData",
		classes = {
				@ConstructorResult(
						targetClass = StoreGravityData.class,
						columns = {
								@ColumnResult(name = "store_id", type = Integer.class),
								@ColumnResult(name = "store_name", type = String.class),
								@ColumnResult(name = "site_id", type = Integer.class),
								@ColumnResult(name = "latitude", type = Float.class),
								@ColumnResult(name = "longitude", type = Float.class),
								@ColumnResult(name = "volume_total", type = Integer.class),
								@ColumnResult(name = "area_sales", type = Float.class),
								@ColumnResult(name = "store_fit"),
								@ColumnResult(name = "banner_id", type = Integer.class)
						}
				)
		}
)

@NamedNativeQueries({
		@NamedNativeQuery(
				name = "getStoreGravityDataForProjectBg",
				query = "SELECT " +
						"st.store_id, " +
						"st.store_name, " +
						"st.site_id, " +
						"round(si.latitude, 6) AS latitude, " +
						"round(si.longitude, 6) AS longitude, " +
						"v.volume_total, " +
						"st.area_sales, " +
						"st.store_fit, " +
						"st.banner_id " +
						"FROM store st " +
						"JOIN site si ON si.site_id = st.site_id " +
						"JOIN store_casing stc ON stc.store_id = st.store_id " +
						"JOIN store_casing_project scp ON scp.store_casing_id = stc.store_casing_id " +
						"JOIN store_volume v ON stc.store_volume_id = v.store_volume_id " +
						"WHERE st.deleted_date IS NULL " +
						"AND stc.deleted_date IS NULL " +
						"AND st.is_float = 0 " +
						"AND scp.project_id = :projectId " +
						"AND st.area_sales IS NOT null",
				resultSetMapping = "StoreGravityData",
				resultClass = StoreGravityData.class
		)
})


@Entity
@Table
public class StoreGravityData {

	@Id
	private Integer storeId;
	private String storeName;
	private Integer siteId;
	private Float latitude;
	private Float longitude;
	private Integer volume;
	private Float salesArea;
	private String storeFit;
	private Integer bannerId;

	public StoreGravityData() {

	}

	public StoreGravityData(Integer storeId, String storeName, Integer siteId, Float latitude, Float longitude, Integer volume, Float salesArea, String storeFit, Integer bannerId) {
		this.storeId = storeId;
		this.storeName = storeName;
		this.siteId = siteId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.volume = volume;
		this.salesArea = salesArea;
		this.storeFit = storeFit;
		this.bannerId = bannerId;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
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

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public Float getSalesArea() {
		return salesArea;
	}

	public void setSalesArea(Float salesArea) {
		this.salesArea = salesArea;
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
}
