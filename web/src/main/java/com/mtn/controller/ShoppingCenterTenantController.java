package com.mtn.controller;

import com.mtn.model.domain.ShoppingCenterTenant;
import com.mtn.model.simpleView.SimpleShoppingCenterTenantView;
import com.mtn.model.view.ShoppingCenterTenantView;
import com.mtn.service.ShoppingCenterTenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Allen on 5/4/2017.
 */
@RestController
@RequestMapping("/api/shopping-center-tenant")
public class ShoppingCenterTenantController extends CrudControllerImpl<ShoppingCenterTenant> {

    @Autowired
    private ShoppingCenterTenantService shoppingCenterTenantService;

    @Override
    public ShoppingCenterTenantService getEntityService() {
        return shoppingCenterTenantService;
    }

    @Override
    public Object getViewFromModel(ShoppingCenterTenant model) {
        return new ShoppingCenterTenantView(model);
    }

    @Override
    public Object getSimpleViewFromModel(ShoppingCenterTenant model) {
        return new SimpleShoppingCenterTenantView(model);
    }
}
