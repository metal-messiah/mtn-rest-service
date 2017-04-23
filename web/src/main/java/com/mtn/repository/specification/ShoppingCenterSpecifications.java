package com.mtn.repository.specification;

import com.mtn.model.domain.ShoppingCenter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 4/23/2017.
 */
public class ShoppingCenterSpecifications {

    private static final String DELETED_DATE = "deletedDate";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String OWNER = "owner";

    public static Specification<ShoppingCenter> queryWhereNotDeleted() {
        return new Specification<ShoppingCenter>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenter> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return isNotDeleted().toPredicate(root, criteriaQuery, criteriaBuilder);
            }
        };
    }

    public static Specification<ShoppingCenter> queryWhereIdEquals(Integer value) {
        return new Specification<ShoppingCenter>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenter> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                predicates.add(idEquals(value).toPredicate(root, criteriaQuery, criteriaBuilder));
                predicates.add(isNotDeleted().toPredicate(root, criteriaQuery, criteriaBuilder));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

    public static Specification<ShoppingCenter> queryWhereNameContains(String value) {
        return new Specification<ShoppingCenter>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenter> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                predicates.add(nameContains(value).toPredicate(root, criteriaQuery, criteriaBuilder));
                predicates.add(isNotDeleted().toPredicate(root, criteriaQuery, criteriaBuilder));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

    public static Specification<ShoppingCenter> queryWhereOwnerContains(String value) {
        return new Specification<ShoppingCenter>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenter> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                predicates.add(ownerContains(value).toPredicate(root, criteriaQuery, criteriaBuilder));
                predicates.add(isNotDeleted().toPredicate(root, criteriaQuery, criteriaBuilder));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

    public static Specification<ShoppingCenter> queryWhereNameOrOwnerContains(String value) {
        return new Specification<ShoppingCenter>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenter> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> orPredicates = new ArrayList<>();
                orPredicates.add(nameContains(value).toPredicate(root, criteriaQuery, criteriaBuilder));
                orPredicates.add(ownerContains(value).toPredicate(root, criteriaQuery, criteriaBuilder));

                List<Predicate> andPredicates = new ArrayList<>();
                andPredicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[orPredicates.size()])));
                andPredicates.add(isNotDeleted().toPredicate(root, criteriaQuery, criteriaBuilder));

                return criteriaBuilder.and(andPredicates.toArray(new Predicate[andPredicates.size()]));
            }
        };
    }

    private static Specification<ShoppingCenter> nameContains(String value) {
        return new Specification<ShoppingCenter>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenter> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get(NAME)), String.format("%%%s%%", value.toLowerCase()));
            }
        };
    }

    private static Specification<ShoppingCenter> ownerContains(String value) {
        return new Specification<ShoppingCenter>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenter> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get(OWNER)), String.format("%%%s%%", value.toLowerCase()));
            }
        };
    }

    private static Specification<ShoppingCenter> idEquals(Integer id) {
        return new Specification<ShoppingCenter>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenter> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(ID), id);
            }
        };
    }

    private static Specification<ShoppingCenter> isNotDeleted() {
        return new Specification<ShoppingCenter>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenter> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(DELETED_DATE));
            }
        };
    }
}
