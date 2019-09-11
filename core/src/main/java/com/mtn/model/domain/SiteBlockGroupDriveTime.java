package com.mtn.model.domain;

import javax.persistence.*;

@Entity
@Table
public class SiteBlockGroupDriveTime {

    @Id
	@GeneratedValue
    private Integer id;

    private Integer driveSeconds;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id")
	private Site site;

	private String fips;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDriveSeconds() {
        return driveSeconds;
    }

    public void setDriveSeconds(Integer driveSeconds) {
        this.driveSeconds = driveSeconds;
    }

    public String getFips() {
        return fips;
    }

    public void setFips(String fips) {
        this.fips = fips;
    }


    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

}
