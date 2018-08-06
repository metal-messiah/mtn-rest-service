package com.mtn.service;

import com.mtn.model.domain.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BannerService extends EntityService<Banner> {

	Page<Banner> findAllByQueryUsingSpecs(Pageable page, String query);

	Banner findOneByBannerName(String name);
}
