package com.mtn.controller;

import com.mtn.model.domain.*;
import com.mtn.model.simpleView.*;
import com.mtn.model.view.BannerView;
import com.mtn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/banner")
public class BannerController extends CrudControllerImpl<Banner> {

    @Autowired
    private BannerService bannerService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll(Pageable page, @RequestParam(value = "query", required = false) String query) {
        Page<Banner> domainModels;
        if (query != null) {
            domainModels = getEntityService().findAllByQueryUsingSpecs(page, query);
        } else {
            domainModels = getEntityService().findAllUsingSpecs(page);
        }
        return ResponseEntity.ok(domainModels.map(this::getSimpleViewFromModel));
    }

    @Override
    public BannerService getEntityService() {
        return bannerService;
    }

    @Override
    public Object getViewFromModel(Banner model) {
        return new BannerView(model);
    }

    @Override
    public Object getSimpleViewFromModel(Banner model) {
        return new SimpleBannerView(model);
    }
}
