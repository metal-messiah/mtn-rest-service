package com.mtn.repository.specification;

import com.mtn.model.domain.UserIdentity;
import com.mtn.model.domain.UserProfile;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 4/23/2017.
 */
public class UserProfileSpecifications {

    private static final String DELETED_DATE = "deletedDate";
    private static final String EMAIL = "email";
    private static final String ID = "id";
    private static final String IDENTITIES = "identities";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String SYSTEM_ADMINISTRATOR_EMAIL = "system.administrator@mtnra.com";
    private static final String PROVIDER_USER_ID = "providerUserId";

    public static Specification<UserProfile> queryWhereNotSystemAdministratorAndNotDeleted() {
        return new Specification<UserProfile>() {
            @Override
            public Predicate toPredicate(Root<UserProfile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                predicates.add(isNotSystemAdministrator().toPredicate(root, criteriaQuery, criteriaBuilder));
                predicates.add(isNotDeleted().toPredicate(root, criteriaQuery, criteriaBuilder));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

    public static Specification<UserProfile> queryWhereEmailOrFirstNameOrLastNameContains(String value) {
        return new Specification<UserProfile>() {
            @Override
            public Predicate toPredicate(Root<UserProfile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> orPredicates = new ArrayList<>();

                orPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(EMAIL)), value.toLowerCase() + "%"));
                orPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(FIRST_NAME)), value.toLowerCase() + "%"));
                orPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(LAST_NAME)), value.toLowerCase() + "%"));

                List<Predicate> andPredicates = new ArrayList<>();

                andPredicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[orPredicates.size()])));
                andPredicates.add(queryWhereNotSystemAdministratorAndNotDeleted().toPredicate(root, criteriaQuery, criteriaBuilder));

                return criteriaBuilder.and(andPredicates.toArray(new Predicate[andPredicates.size()]));
            }
        };
    }

    public static Specification<UserProfile> queryWhereEmailEquals(String value) {
        return new Specification<UserProfile>() {
            @Override
            public Predicate toPredicate(Root<UserProfile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                predicates.add(emailEquals(value).toPredicate(root, criteriaQuery, criteriaBuilder));
                predicates.add(queryWhereNotSystemAdministratorAndNotDeleted().toPredicate(root, criteriaQuery, criteriaBuilder));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

    public static Specification<UserProfile> queryWhereIdEquals(Integer value) {
        return new Specification<UserProfile>() {
            @Override
            public Predicate toPredicate(Root<UserProfile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                predicates.add(idEquals(value).toPredicate(root, criteriaQuery, criteriaBuilder));
                predicates.add(queryWhereNotSystemAdministratorAndNotDeleted().toPredicate(root, criteriaQuery, criteriaBuilder));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

    public static Specification<UserProfile> queryWhereProviderUserIdEquals(String value) {
        return new Specification<UserProfile>() {
            @Override
            public Predicate toPredicate(Root<UserProfile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                predicates.add(identityUserProviderIdEquals(value).toPredicate(root, criteriaQuery, criteriaBuilder));
                predicates.add(queryWhereNotSystemAdministratorAndNotDeleted().toPredicate(root, criteriaQuery, criteriaBuilder));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

    private static Specification<UserProfile> emailEquals(String value) {
        return new Specification<UserProfile>() {
            @Override
            public Predicate toPredicate(Root<UserProfile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(EMAIL), value.toLowerCase());
            }
        };
    }

    private static Specification<UserProfile> idEquals(Integer id) {
        return new Specification<UserProfile>() {
            @Override
            public Predicate toPredicate(Root<UserProfile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(ID), id);
            }
        };
    }

    private static Specification<UserProfile> identityUserProviderIdEquals(String providerUserId) {
        return new Specification<UserProfile>() {
            @Override
            public Predicate toPredicate(Root<UserProfile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<UserProfile, UserIdentity> userIdentityJoin = root.join(IDENTITIES);
                return criteriaBuilder.equal(userIdentityJoin.get(PROVIDER_USER_ID), providerUserId);
            }
        };
    }

    private static Specification<UserProfile> isNotDeleted() {
        return new Specification<UserProfile>() {
            @Override
            public Predicate toPredicate(Root<UserProfile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(DELETED_DATE));
            }
        };
    }

    private static Specification<UserProfile> isNotSystemAdministrator() {
        return new Specification<UserProfile>() {
            @Override
            public Predicate toPredicate(Root<UserProfile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.notEqual(root.get(EMAIL), SYSTEM_ADMINISTRATOR_EMAIL);
            }
        };
    }

}
