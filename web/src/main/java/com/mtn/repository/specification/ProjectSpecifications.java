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
public class ProjectSpecifications {

    private static final String DELETED_DATE = "deletedDate";
    private static final String ID = "id";
    private static final String ACTIVE = "isActive";
    private static final String PRIMARY_DATA = "isPrimaryData";
    private static final String PROJECT_NAME = "projectName";

    public static Specification<Project> idEquals(Integer id) {
        return new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(PROJECT_NAME)));
                return criteriaBuilder.equal(root.get(ID), id);
            }
        };
    }

    public static Specification<Project> isNotDeleted() {
        return new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(PROJECT_NAME)));
                return criteriaBuilder.isNull(root.get(DELETED_DATE));
            }
        };
    }

    public static Specification<Project> isActive() {
        return new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(PROJECT_NAME)));
                return criteriaBuilder.isTrue(root.get(ACTIVE));
            }
        };
    }

    public static Specification<Project> isPrimaryData() {
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
}
