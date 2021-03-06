package com.mtn.model.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table
@AttributeOverride(name="id", column=@Column(name="resource_quota_id"))
public class ResourceQuota extends AuditingEntity{

    private String resourceName;
    private LocalDateTime periodStartDate;
    private Integer queryCount;
    private Integer quotaLimit;

    public String getResourceName() {
        System.out.println("GETTING RESOURCE NAME (RQ)");
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public LocalDateTime getPeriodStartDate() {
        return periodStartDate;
    }

    public void setPeriodStartDate(LocalDateTime periodStartDate) {
        this.periodStartDate = periodStartDate;
    }

    public Integer getQueryCount() {
        return queryCount;
    }

    public void setQueryCount(Integer queryCount) {
        this.queryCount = queryCount;
    }

    public Integer getQuotaLimit() {
        return quotaLimit;
    }

    public void setQuotaLimit(Integer quotaLimit) {
        this.quotaLimit = quotaLimit;
    }
}
