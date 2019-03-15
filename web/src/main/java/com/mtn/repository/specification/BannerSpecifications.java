package com.mtn.repository.specification;

import com.mtn.model.domain.Banner;
import com.mtn.model.domain.Banner_;
import org.springframework.data.jpa.domain.Specification;

public class BannerSpecifications extends AuditingEntitySpecifications {

    public static Specification<Banner> bannerNameLike(String nameQuery) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(Banner_.bannerName)), String.format("%%%s%%", nameQuery.toLowerCase()));
    }

}
