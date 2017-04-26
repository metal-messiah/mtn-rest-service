package com.mtn.model.domain;

import com.mtn.constant.StoreFitType;
import com.mtn.constant.StoreFormatType;
import com.mtn.model.view.SimpleStoreView;
import com.mtn.model.view.StoreView;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Allen on 4/26/2017.
 */
@Entity
@Table
public class Store extends AuditingEntity {

    private Integer id;
    private Site site;
    private String name;
    private StoreFitType fit;
    private StoreFormatType format;
    private Boolean isActive = false;
    private Integer areaSales;
    private Double areaSalesPercentOfTotal;
    private Integer areaTotal;
    private Boolean areaIsEstimate = true;
    private LocalDateTime openedDate;
    private LocalDateTime closedDate;
    private Integer version;

    public Store() {
    }

    public Store(SimpleStoreView simpleStoreView) {
        this.id = simpleStoreView.getId();
        this.name = simpleStoreView.getName();
        this.fit = simpleStoreView.getFit();
        this.format = simpleStoreView.getFormat();
        this.isActive = simpleStoreView.getActive();
        this.areaSales = simpleStoreView.getAreaSales();
        this.areaSalesPercentOfTotal = simpleStoreView.getAreaSalesPercentOfTotal();
        this.areaTotal = simpleStoreView.getAreaTotal();
        this.areaIsEstimate = simpleStoreView.getAreaIsEstimate();
        this.openedDate = simpleStoreView.getOpenedDate();
        this.closedDate = simpleStoreView.getClosedDate();
        this.version = simpleStoreView.getVersion();
    }

    public Store(StoreView storeView) {
        this((SimpleStoreView) storeView);

        if (storeView.getSite() != null) {
            this.site = new Site(storeView.getSite());
        }
    }

    @PrePersist
    public void prePersist() {
        version = 1;
    }

    @PreUpdate
    public void preUpdate() {
        version++;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_store_id")
    @SequenceGenerator(name = "seq_store_id", sequenceName = "seq_store_id", allocationSize = 1)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "site_id")
    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Enumerated(EnumType.STRING)
    public StoreFitType getFit() {
        return fit;
    }

    public void setFit(StoreFitType fit) {
        this.fit = fit;
    }

    @Enumerated(EnumType.STRING)
    public StoreFormatType getFormat() {
        return format;
    }

    public void setFormat(StoreFormatType format) {
        this.format = format;
    }

    @Column(name = "is_active")
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getAreaSales() {
        return areaSales;
    }

    public void setAreaSales(Integer areaSales) {
        this.areaSales = areaSales;
    }

    public Double getAreaSalesPercentOfTotal() {
        return areaSalesPercentOfTotal;
    }

    public void setAreaSalesPercentOfTotal(Double areaSalesPercentOfTotal) {
        this.areaSalesPercentOfTotal = areaSalesPercentOfTotal;
    }

    public Integer getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(Integer areaTotal) {
        this.areaTotal = areaTotal;
    }

    public Boolean getAreaIsEstimate() {
        return areaIsEstimate;
    }

    public void setAreaIsEstimate(Boolean areaIsEstimate) {
        this.areaIsEstimate = areaIsEstimate;
    }

    public LocalDateTime getOpenedDate() {
        return openedDate;
    }

    public void setOpenedDate(LocalDateTime openedDate) {
        this.openedDate = openedDate;
    }

    public LocalDateTime getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(LocalDateTime closedDate) {
        this.closedDate = closedDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
