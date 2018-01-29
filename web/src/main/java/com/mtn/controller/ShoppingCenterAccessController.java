package com.mtn.controller;

import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.model.view.ShoppingCenterAccessView;
import com.mtn.service.ShoppingCenterAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Allen on 5/4/2017.
 */
@RestController
@RequestMapping("/api/shopping-center-access")
public class ShoppingCenterAccessController extends CrudControllerImpl<ShoppingCenterAccess> {

    @Autowired
    private ShoppingCenterAccessService accessService;


    @Override
    public ShoppingCenterAccessService getEntityService() {
        return accessService;
    }

    @Override
    public ShoppingCenterAccessView getViewFromModel(Object model) {
        return new ShoppingCenterAccessView((ShoppingCenterAccess) model);
    }
}
