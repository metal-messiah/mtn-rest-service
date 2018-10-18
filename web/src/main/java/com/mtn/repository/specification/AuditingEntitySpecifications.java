package com.mtn.repository.specification;

import com.mtn.model.domain.AuditingEntity;
import com.mtn.model.domain.Banner;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Tyler on 2/14/2018.
 */
public class AuditingEntitySpecifications {

    protected static final String DELETED_DATE = "deletedDate";
    protected static final String ID = "id";

    public static <T extends AuditingEntity> Specification<T> idIn(List<Integer> ids) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return root.get(ID).in(ids);
            }
        };
    }

    public static <T extends AuditingEntity> Specification<T> idEquals(Integer id) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(ID), id);
            }
        };
    }

    public static <T extends AuditingEntity> Specification<T> isNotDeleted() {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(DELETED_DATE));
            }
        };
    }

}
