package com.mtn.service;

import com.mtn.model.domain.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BannerService extends EntityService<Banner> {

	Page<Banner> findAllWhereBannerNameLike(String name, Pageable page);

	Banner findOneByBannerName(String name);
}
