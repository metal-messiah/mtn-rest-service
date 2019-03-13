package com.mtn.controller;

import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.simpleView.SimpleShoppingCenterView;
import com.mtn.model.view.*;
import com.mtn.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shopping-center")
public class ShoppingCenterController extends CrudController<ShoppingCenter, ShoppingCenterView> {

    @Autowired
    public ShoppingCenterController(ShoppingCenterService shoppingCenterService) {
        super(shoppingCenterService, ShoppingCenterView::new);
    }

    @GetMapping
    public ResponseEntity findAll(
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "owner", required = false) String owner,
            Pageable page
    ) {
        Page<ShoppingCenter> domainModels;
        if (StringUtils.isNotBlank(q)) {
            domainModels = ((ShoppingCenterService) this.entityService).findAllByNameOrOwnerUsingSpecs(q, page);
        } else if (StringUtils.isNotBlank(name)) {
            domainModels = ((ShoppingCenterService) this.entityService).findAllByNameUsingSpecs(name, page);
        } else if (StringUtils.isNotBlank(owner)) {
            domainModels = ((ShoppingCenterService) this.entityService).findAllByOwnerUsingSpecs(owner, page);
        } else {
            domainModels = this.entityService.findAllUsingSpecs(page);
        }

        return ResponseEntity.ok(domainModels.map(SimpleShoppingCenterView::new));
    }

}
