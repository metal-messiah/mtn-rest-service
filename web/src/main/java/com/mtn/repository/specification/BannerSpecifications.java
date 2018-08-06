package com.mtn.repository.specification;

import com.mtn.model.domain.Banner;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Tyler on 2/14/2018.
 */
public class BannerSpecifications extends AuditingEntitySpecifications {

    private static final String BANNER_NAME = "bannerName";

    public static Specification<Banner> bannerNameLike(String nameQuery) {
        return new Specification<Banner>() {
            @Override
            public Predicate toPredicate(Root<Banner> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(BANNER_NAME)));
                return criteriaBuilder.like(criteriaBuilder.lower(root.get(BANNER_NAME)), String.format("%%%s%%", nameQuery.toLowerCase()));

            }
        };
    }

    public static Specification<Banner> bannerNameEquals(String name) {
        return new Specification<Banner>() {
            @Override
            public Predicate toPredicate(Root<Banner> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(BANNER_NAME)));
                return criteriaBuilder.equal(root.get(BANNER_NAME), name);
            }
        };
    }
}
