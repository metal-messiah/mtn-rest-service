package com.mtn.repository.specification;

import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.ShoppingCenterSurvey;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * Created by Allen on 4/23/2017.
 */
public class ShoppingCenterSurveySpecifications {

    private static final String DELETED_DATE = "deletedDate";
    private static final String ID = "id";
    private static final String SHOPPING_CENTER = "shoppingCenter";

    public static Specification<ShoppingCenterSurvey> idEquals(Integer id) {
        return new Specification<ShoppingCenterSurvey>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenterSurvey> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(ID), id);
            }
        };
    }

    public static Specification<ShoppingCenterSurvey> shoppingCenterIdEquals(Integer id) {
        return new Specification<ShoppingCenterSurvey>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenterSurvey> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<ShoppingCenterSurvey, ShoppingCenter> shoppingCenterSurveyShoppingCenterJoin = root.join(SHOPPING_CENTER);
                return criteriaBuilder.equal(shoppingCenterSurveyShoppingCenterJoin.get(ID), id);
            }
        };
    }

    public static Specification<ShoppingCenterSurvey> isNotDeleted() {
        return new Specification<ShoppingCenterSurvey>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenterSurvey> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(DELETED_DATE));
            }
        };
    }
}
