package com.mtn.controller;

import com.mtn.model.domain.ShoppingCenterTenant;
import com.mtn.model.view.ShoppingCenterTenantView;
import com.mtn.service.ShoppingCenterTenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shopping-center-tenant")
public class ShoppingCenterTenantController extends CrudController<ShoppingCenterTenant, ShoppingCenterTenantView> {

    @Autowired
    public ShoppingCenterTenantController(ShoppingCenterTenantService shoppingCenterTenantService) {
        super(shoppingCenterTenantService, ShoppingCenterTenantView::new);
    }

}
