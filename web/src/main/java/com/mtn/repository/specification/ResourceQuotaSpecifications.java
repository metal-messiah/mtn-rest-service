package com.mtn.repository.specification;

import com.mtn.model.domain.ResourceQuota;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.time.LocalDateTime;

/**
 * Created by Tyler on 2/14/2018.
 */
public class ResourceQuotaSpecifications extends AuditingEntitySpecifications {

    private static final String RESOURCE_NAME = "resourceName";
    private static final String PERIOD_START_DATE = "periodStartDate";
    private static final String RESOURCE_QUOTA_ID = "id";

    public static Specification<ResourceQuota> resourceDateIsGreaterThan(LocalDateTime dateQuery) {
        System.out.println(dateQuery);
        return new Specification<ResourceQuota>() {
            @Override
            public Predicate toPredicate(Root<ResourceQuota> root, CriteriaQuery<?> criteriaQuery,
                    CriteriaBuilder criteriaBuilder) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(PERIOD_START_DATE)));
                return criteriaBuilder.greaterThanOrEqualTo(root.get(PERIOD_START_DATE), dateQuery);
            }
        };
    }

    public static Specification<ResourceQuota> resourceNameLike(String nameQuery) {
        System.out.println(nameQuery);
        return new Specification<ResourceQuota>() {
            @Override
            public Predicate toPredicate(Root<ResourceQuota> root, CriteriaQuery<?> criteriaQuery,
                    CriteriaBuilder criteriaBuilder) {
                System.out.println(root.get(RESOURCE_QUOTA_ID));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get(RESOURCE_QUOTA_ID)));
                return criteriaBuilder.like(criteriaBuilder.lower(root.get(RESOURCE_NAME)), nameQuery.toLowerCase());
            }
        };
    }

    public static Specification<ResourceQuota> resourceNameEquals(String name) {
        return new Specification<ResourceQuota>() {
            @Override
            public Predicate toPredicate(Root<ResourceQuota> root, CriteriaQuery<?> criteriaQuery,
                    CriteriaBuilder criteriaBuilder) {
                System.out.println(root.get(RESOURCE_QUOTA_ID));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get(RESOURCE_QUOTA_ID)));
                return criteriaBuilder.equal(root.get(RESOURCE_NAME), name);
            }
        };
    }
}
