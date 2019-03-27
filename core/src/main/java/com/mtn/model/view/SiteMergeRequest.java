package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SiteMergeRequest {

    private SiteView mergedSite;
    private List<Integer> siteIds;

    public SiteMergeRequest() {
    }

    public SiteView getMergedSite() {
        return mergedSite;
    }

    public void setMergedSite(SiteView mergedSite) {
        this.mergedSite = mergedSite;
    }

    public List<Integer> getSiteIds() {
        return siteIds;
    }

    public void setSiteIds(List<Integer> siteIds) {
        this.siteIds = siteIds;
    }
}
