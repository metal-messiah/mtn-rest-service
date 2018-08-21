package com.mtn.controller;

import com.mtn.model.domain.ClientAccessKey;
import com.mtn.model.view.ClientAccessKeyView;
import com.mtn.service.ClientAccessKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client-access-key")
public class ClientAccessKeyController extends CrudControllerImpl<ClientAccessKey> {

	@Autowired
	private ClientAccessKeyService clientAccessKeyService;

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity validate(@RequestBody ClientAccessKey clientAccessKey) {
		if (clientAccessKey.getAccessKey() == null || clientAccessKey.getClientUniqueIdentifier() == null) {
			return ResponseEntity.badRequest().body("Body must contain 'accessKey' and 'clientUniqueIdentifier'");
		}
		ClientAccessKey key = getEntityService().validateKey(clientAccessKey.getAccessKey(), clientAccessKey.getClientUniqueIdentifier());
		return ResponseEntity.ok(this.getViewFromModel(key));
	}

	@Override
	public ClientAccessKeyService getEntityService() {
		return clientAccessKeyService;
	}

	@Override
	public Object getViewFromModel(ClientAccessKey model) {
		return new ClientAccessKeyView(model);
	}

	@Override
	public Object getSimpleViewFromModel(ClientAccessKey model) {
		return new ClientAccessKeyView(model);
	}
}
