package com.mtn.repository.specification;

import com.mtn.model.domain.Project;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Allen on 4/23/2017.
 */
public class ProjectSpecifications extends AuditingEntitySpecifications {

    private static final String ACTIVE = "active";
    private static final String PRIMARY_DATA = "primaryData";
    private static final String PROJECT_NAME = "projectName";

       public static Specification<Project> active() {
        return new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(PROJECT_NAME)));
                return criteriaBuilder.isTrue(root.get(ACTIVE));
            }
        };
    }

    public static Specification<Project> primaryData() {
        return new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(PROJECT_NAME)));
                return criteriaBuilder.isTrue(root.get(PRIMARY_DATA));
            }
        };
    }

    public static Specification<Project> projectNameIsLike(String nameQuery) {
        return new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(PROJECT_NAME)));
                return criteriaBuilder.like(root.get(PROJECT_NAME), nameQuery);
            }
        };
    }

    public static Specification<Project> projectNameEquals(String projectName) {
        return new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(PROJECT_NAME)));
                return criteriaBuilder.equal(root.get(PROJECT_NAME), projectName);
            }
        };
    }
}
