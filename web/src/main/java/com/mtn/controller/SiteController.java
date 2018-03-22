package com.mtn.controller;

import com.mtn.model.domain.Site;
import com.mtn.model.domain.Store;
import com.mtn.model.simpleView.SimpleSiteView;
import com.mtn.model.simpleView.SimpleStoreView;
import com.mtn.model.view.SiteView;
import com.mtn.model.view.StoreView;
import com.mtn.service.SiteService;
import com.mtn.service.StoreService;
import com.mtn.util.MtnLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 4/25/2017.
 */
@RestController
@RequestMapping("/api/site")
public class SiteController extends CrudControllerImpl<Site> {

    @Autowired
    private SiteService siteService;
    @Autowired
    private StoreService storeService;

    @RequestMapping(value = "/{id}/store", method = RequestMethod.POST)
    public ResponseEntity addOneStoreToSite(
            @PathVariable("id") Integer siteId,
            @RequestParam(value = "overrideActiveStore", defaultValue = "false") Boolean overrideActiveStore,
            @RequestBody Store request) {
        Store domainModel = siteService.addOneStoreToSite(siteId, request, overrideActiveStore);
        return ResponseEntity.ok(new StoreView(domainModel));
    }

    @RequestMapping(value = "/{id}/store", method = RequestMethod.GET)
    public ResponseEntity findAllStoresForSite(@PathVariable("id") Integer siteId) {
        List<Store> domainModels = storeService.findAllBySiteIdUsingSpecs(siteId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleStoreView::new).collect(Collectors.toList()));
    }

    @RequestMapping(method = RequestMethod.GET, params = {"north", "south", "east", "west"})
    public ResponseEntity findAllInBounds(@RequestParam("north") Float north,
                                          @RequestParam("south") Float south,
                                          @RequestParam("east") Float east,
                                          @RequestParam("west") Float west,
                                          Pageable page) {
        Page<Site> domainModels = siteService.findAllInBoundsUsingSpecs(north, south, east, west, page);
        return ResponseEntity.ok(domainModels.map(this::getSimpleViewFromModel));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll(Pageable page) {
        Page<Site> domainModels = siteService.findAllUsingSpecs(page);
        return ResponseEntity.ok(domainModels.map(this::getSimpleViewFromModel));
    }

    @Override
    public SiteService getEntityService() {
        return siteService;
    }

    @Override
    public Object getViewFromModel(Site model) {
        return new SiteView(model);
    }

    @Override
    public Object getSimpleViewFromModel(Site model) {
        return new SimpleSiteView(model);
    }
}
