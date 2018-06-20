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
public class BannerSpecifications {

    private static final String BANNER_NAME = "bannerName";
    private static final String DELETED_DATE = "deletedDate";
    private static final String ID = "id";

    public static Specification<Banner> idEquals(Integer id) {
        return new Specification<Banner>() {
            @Override
            public Predicate toPredicate(Root<Banner> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(ID), id);
            }
        };
    }

    public static Specification<Banner> isNotDeleted() {
        return new Specification<Banner>() {
            @Override
            public Predicate toPredicate(Root<Banner> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(DELETED_DATE));
            }
        };
    }

    public static Specification<Banner> bannerNameIsLike(String nameQuery) {
        return new Specification<Banner>() {
            @Override
            public Predicate toPredicate(Root<Banner> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(BANNER_NAME)));
                return criteriaBuilder.like(root.get(BANNER_NAME), nameQuery);
            }
        };
    }
}
