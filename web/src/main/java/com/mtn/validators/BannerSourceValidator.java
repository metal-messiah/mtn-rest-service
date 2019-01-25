package com.mtn.validators;

import com.mtn.model.domain.BannerSource;
import com.mtn.model.view.BannerSourceView;
import com.mtn.repository.BannerSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannerSourceValidator extends EntityValidator<BannerSource, BannerSourceView> {

	@Autowired
	public BannerSourceValidator(BannerSourceRepository repository) {
		super(repository);
	}

	@Override
	protected void validateUpdateInsertBusinessRules(BannerSourceView request) {
		if (request.getSourceName() == null) {
			throw new IllegalStateException("BannerSource must have a Source Name (ex. Planned Grocery)!");
		}
		if (request.getSourceNativeId() == null) {
			throw new IllegalStateException("BannerSource must have a Source Native Id!");
		}
	}
}
