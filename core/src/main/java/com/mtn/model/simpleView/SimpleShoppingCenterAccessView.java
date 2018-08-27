package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.AccessType;
import com.mtn.model.domain.ShoppingCenterAccess;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleShoppingCenterAccessView extends SimpleAuditingEntityView {

    private AccessType accessType;

    public SimpleShoppingCenterAccessView() {
    }

    public SimpleShoppingCenterAccessView(ShoppingCenterAccess access) {
        super(access);
        this.accessType = access.getAccessType();
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }
}
