package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ResourceQuota;

import com.mtn.constant.ResourceQuotaType;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceQuotaView extends AuditingEntityView {

    private String resourceName;
    private LocalDateTime periodStartDate;
    private Integer queryCount;
    private Integer quotaLimit;

    public ResourceQuotaView() {
    }

    public ResourceQuotaView(ResourceQuota resourceQuota) {
        super(resourceQuota);

        this.resourceName = resourceQuota.getResourceName();
        this.periodStartDate = resourceQuota.getPeriodStartDate();
        this.queryCount = resourceQuota.getQueryCount();
        this.quotaLimit = resourceQuota.getQuotaLimit();
    }

    public String getResourceName() {
        System.out.println("GETTING RESOURCE NAME (RQV)");
        return resourceName;
    }

    public LocalDateTime getPeriodStartDate() {
        return periodStartDate;
    }

    public Integer getQueryCount() {
        return queryCount;
    }

    public Integer getQuotaLimit() {
        return quotaLimit;
    }
}
