package com.mtn.repository.specification;

import com.mtn.model.domain.ShoppingCenterSurvey;
import com.mtn.model.domain.ShoppingCenterTenant;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * Created by Allen on 4/23/2017.
 */
public class ShoppingCenterTenantSpecifications {

    private static final String DELETED_DATE = "deletedDate";
    private static final String ID = "id";
    private static final String SHOPPING_CENTER_SURVEY = "survey";

    public static Specification<ShoppingCenterTenant> idEquals(Integer id) {
        return new Specification<ShoppingCenterTenant>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenterTenant> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(ID), id);
            }
        };
    }

    public static Specification<ShoppingCenterTenant> shoppingCenterSurveyIdEquals(Integer id) {
        return new Specification<ShoppingCenterTenant>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenterTenant> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<ShoppingCenterTenant, ShoppingCenterSurvey> shoppingCenterTenantShoppingCenterSurveyJoin = root.join(SHOPPING_CENTER_SURVEY);
                return criteriaBuilder.equal(shoppingCenterTenantShoppingCenterSurveyJoin.get(ID), id);
            }
        };
    }

    public static Specification<ShoppingCenterTenant> isNotDeleted() {
        return new Specification<ShoppingCenterTenant>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenterTenant> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(DELETED_DATE));
            }
        };
    }
}
