package com.mtn.controller;

import com.mtn.model.domain.ShoppingCenterCasing;
import com.mtn.model.view.ShoppingCenterCasingView;
import com.mtn.service.ShoppingCenterCasingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shopping-center-casing")
public class ShoppingCenterCasingController extends CrudController<ShoppingCenterCasing, ShoppingCenterCasingView> {

    @Autowired
    public ShoppingCenterCasingController(ShoppingCenterCasingService shoppingCenterCasingService) {
        super(shoppingCenterCasingService, ShoppingCenterCasingView::new);
    }
}
