package com.mtn.repository.specification;

import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.model.domain.ShoppingCenterSurvey;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * Created by Allen on 4/23/2017.
 */
public class ShoppingCenterAccessSpecifications extends AuditingEntitySpecifications{

    private static final String ID = "id";
    private static final String SHOPPING_CENTER_SURVEY = "survey";

    public static Specification<ShoppingCenterAccess> shoppingCenterSurveyIdEquals(Integer id) {
        return new Specification<ShoppingCenterAccess>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenterAccess> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<ShoppingCenterAccess, ShoppingCenterSurvey> shoppingCenterAccessShoppingCenterSurveyJoin = root.join(SHOPPING_CENTER_SURVEY);
                return criteriaBuilder.equal(shoppingCenterAccessShoppingCenterSurveyJoin.get(ID), id);
            }
        };
    }
}
