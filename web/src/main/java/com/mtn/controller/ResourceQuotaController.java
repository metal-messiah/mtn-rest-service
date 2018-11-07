package com.mtn.controller;

import com.mtn.model.domain.ResourceQuota;
import com.mtn.model.view.ResourceQuotaView;
import com.mtn.service.ResourceQuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resource-quota")
public class ResourceQuotaController extends CrudController<ResourceQuota, ResourceQuotaView> {

    @Autowired
    public ResourceQuotaController(ResourceQuotaService entityService) {
        super(entityService, ResourceQuotaView::new);
    }

    @GetMapping(value = "/newest", params = {"name"})
    public ResponseEntity findNewestRecordByName(@RequestParam(value = "name") String name) {
        ResourceQuota domainModel = ((ResourceQuotaService) this.entityService).findNewestRecordByName(name);
        return ResponseEntity.ok(new ResourceQuotaView(domainModel));
    }

}
