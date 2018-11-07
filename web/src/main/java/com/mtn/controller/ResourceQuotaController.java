package com.mtn.controller;

import com.mtn.model.domain.ResourceQuota;
import com.mtn.model.view.ResourceQuotaView;
import com.mtn.service.ResourceQuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/resource-quota")
public class ResourceQuotaController extends CrudController<ResourceQuota, ResourceQuotaView> {

    @Autowired
    public ResourceQuotaController(ResourceQuotaService entityService) {
        super(entityService, ResourceQuotaView::new);
    }

    @GetMapping(value = "/newest")
    public ResponseEntity findNewestRecordByName(@PageableDefault(value = 1) Pageable page,
            @RequestParam(value = "name", required = false) String name) {
        System.out.println("FIND ALL (RQC)");
        Page<ResourceQuota> domainModels;

        if (name != null) {
            domainModels = ((ResourceQuotaService) this.entityService).findNewestRecordByName(page, name);

        } else {
            domainModels = this.entityService.findAllUsingSpecs(page);
        }
        return ResponseEntity.ok(domainModels.map(ResourceQuotaView::new));
    }

    @Override
	@PostMapping
	final public ResponseEntity<ResourceQuotaView> addOne(@RequestBody ResourceQuotaView request) {
		ResourceQuota domainModel = ((ResourceQuotaService) this.entityService).addOne(request);
		return ResponseEntity.ok(new ResourceQuotaView(domainModel));
    }

    @PutMapping
	public ResponseEntity updateOne(@RequestBody ResourceQuotaView request ) {
        
		ResourceQuota domainModel = ((ResourceQuotaService) this.entityService).updateOne(request);
		return ResponseEntity.ok(new ResourceQuotaView(domainModel));
	}
    
}
