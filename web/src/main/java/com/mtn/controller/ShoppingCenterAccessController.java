package com.mtn.controller;

import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.model.view.ShoppingCenterAccessView;
import com.mtn.service.ShoppingCenterAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shopping-center-access")
public class ShoppingCenterAccessController extends CrudController<ShoppingCenterAccess, ShoppingCenterAccessView> {

    @Autowired
    public ShoppingCenterAccessController(ShoppingCenterAccessService accessService) {
        super(accessService, ShoppingCenterAccessView::new);
    }

}
