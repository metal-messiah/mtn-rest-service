package com.mtn.repository;

import com.mtn.model.domain.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Tyler on 2/14/2018.
 */
public interface BannerRepository extends JpaRepository<Banner, Integer>, JpaSpecificationExecutor<Banner> {

    Page<Banner> findAllByBannerNameLikeAndDeletedDateIsNull(String name, Pageable page);

    Banner findOneByBannerName(String name);
}
