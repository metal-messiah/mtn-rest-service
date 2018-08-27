package com.mtn.controller;

import com.mtn.model.domain.ClientAccessKey;
import com.mtn.model.view.ClientAccessKeyView;
import com.mtn.service.ClientAccessKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client-access-key")
public class ClientAccessKeyController extends CrudController<ClientAccessKey, ClientAccessKeyView> {

	@Autowired
	public ClientAccessKeyController(ClientAccessKeyService clientAccessKeyService) {
		super(clientAccessKeyService, ClientAccessKeyView::new);
	}

	@Override
	@PutMapping
	public ResponseEntity updateOne(@RequestBody ClientAccessKeyView clientAccessKey) {
		if (clientAccessKey.getAccessKey() == null || clientAccessKey.getClientUniqueIdentifier() == null) {
			return ResponseEntity.badRequest().body("Body must contain 'accessKey' and 'clientUniqueIdentifier'");
		}
		ClientAccessKey key = ((ClientAccessKeyService) this.entityService).validateKey(clientAccessKey.getAccessKey(), clientAccessKey.getClientUniqueIdentifier());
		return ResponseEntity.ok(new ClientAccessKeyView(key));
	}
}
