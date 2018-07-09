package com.mtn.repository.specification;

import com.mtn.model.domain.ExtractionField;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Tyler on 7/5/2018.
 */
public class ExtractionFieldSpecifications {

    private static final String ID = "id";
    private static final String DELETED_DATE = "deletedDate";

    public static Specification<ExtractionField> idEquals(Integer id) {
        return new Specification<ExtractionField>() {
            @Override
            public Predicate toPredicate(Root<ExtractionField> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(ID), id);
            }
        };
    }

    public static Specification<ExtractionField> isNotDeleted() {
        return new Specification<ExtractionField>() {
            @Override
            public Predicate toPredicate(Root<ExtractionField> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(DELETED_DATE));
            }
        };
    }
}
