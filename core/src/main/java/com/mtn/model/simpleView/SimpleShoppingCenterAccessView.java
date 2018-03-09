package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.AccessType;
import com.mtn.model.domain.ShoppingCenterAccess;

/**
 * Created by Allen on 5/4/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleShoppingCenterAccessView {

    private Integer id;
    private AccessType accessType;

    public SimpleShoppingCenterAccessView(ShoppingCenterAccess access) {
        this.id = access.getId();
        this.accessType = access.getAccessType();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }
}
