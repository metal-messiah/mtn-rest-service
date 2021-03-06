package com.mtn.service;

import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreCasing;
import com.mtn.model.domain.StoreStatus;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.utils.StoreUtil;
import com.mtn.model.view.StoreStatusView;
import com.mtn.repository.StoreStatusRepository;
import com.mtn.validators.StoreStatusValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class StoreStatusService extends StoreChildService<StoreStatus, StoreStatusView> {

	@Autowired
	public StoreStatusService(SecurityService securityService,
							  StoreStatusRepository repository,
							  StoreStatusValidator validator) {
		super(securityService, repository, validator, StoreStatus::new);
	}

	@Transactional
	public StoreStatus addOneToStore(StoreStatusView request, Store store) {
		this.validator.validateForInsert(request);

		Optional<StoreStatus> any = store.getStatuses().stream().filter(status -> status.getStatusStartDate().equals(request.getStatusStartDate()) && status.getStatus().equals(request.getStatus()))
				.findAny();
		if (any.isPresent()) {
			throw new IllegalArgumentException("Duplicate status on the same date!");
		}

		UserProfile currentUser = this.securityService.getCurrentUser();

		StoreStatus status = createNewEntityFromRequest(request);
		status.setCreatedBy(currentUser);
		status.setUpdatedBy(currentUser);

		status.setStore(store);
		store.getStatuses().add(status);

		return this.repository.save(status);
	}

	@Transactional
	public void updateStoreStatusesFromCasing(StoreCasing casing) {
		Optional<StoreStatus> storeStatus = StoreUtil.getLatestStatusAsOfDateTime(casing.getStore(), casing.getCasingDate());
		if (!storeStatus.isPresent() || storeStatus.get().getStatus().equals(casing.getStoreStatus())) {
			StoreStatus newStatus = new StoreStatus();
			newStatus.setStatusStartDate(casing.getCasingDate());
			newStatus.setStatus("Open");
			newStatus.setStore(casing.getStore());
		}
	}

	@Override
	protected void setEntityAttributesFromRequest(StoreStatus status, StoreStatusView request) {
		status.setStatus(request.getStatus());
		status.setStatusStartDate(request.getStatusStartDate());
	}

	@Override
	public void handleAssociationsOnDeletion(StoreStatus existing) {
		// Do Nothing
	}
}
