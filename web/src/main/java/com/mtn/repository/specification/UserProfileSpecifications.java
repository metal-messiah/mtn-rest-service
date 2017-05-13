package com.mtn.repository.specification;

import com.mtn.model.domain.UserProfile;
import com.mtn.model.domain.auth.Group;
import com.mtn.model.domain.auth.Role;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * Created by Allen on 4/23/2017.
 */
public class UserProfileSpecifications {

    private static final String DELETED_DATE = "deletedDate";
    private static final String EMAIL = "email";
    private static final String ID = "id";
    private static final String IDENTITIES = "identities";
    private static final String FIRST_NAME = "firstName";
    private static final String GROUP = "group";
    private static final String LAST_NAME = "lastName";
    private static final String SYSTEM_ADMINISTRATOR_EMAIL = "system.administrator@mtnra.com";
    private static final String ROLE = "role";

    public static Specification<UserProfile> emailContains(String value) {
        return new Specification<UserProfile>() {
            @Override
            public Predicate toPredicate(Root<UserProfile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get(EMAIL)), String.format("%%%s%%", value.toLowerCase()));
            }
        };
    }

    public static Specification<UserProfile> firstNameContains(String value) {
        return new Specification<UserProfile>() {
            @Override
            public Predicate toPredicate(Root<UserProfile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get(FIRST_NAME)), String.format("%%%s%%", value.toLowerCase()));
            }
        };
    }

    public static Specification<UserProfile> lastNameContains(String value) {
        return new Specification<UserProfile>() {
            @Override
            public Predicate toPredicate(Root<UserProfile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get(LAST_NAME)), String.format("%%%s%%", value.toLowerCase()));
            }
        };
    }

    public static Specification<UserProfile> idEquals(Integer id) {
        return new Specification<UserProfile>() {
            @Override
            public Predicate toPredicate(Root<UserProfile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(ID), id);
            }
        };
    }

    public static Specification<UserProfile> isNotDeleted() {
        return new Specification<UserProfile>() {
            @Override
            public Predicate toPredicate(Root<UserProfile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(DELETED_DATE));
            }
        };
    }

    public static Specification<UserProfile> isNotSystemAdministrator() {
        return new Specification<UserProfile>() {
            @Override
            public Predicate toPredicate(Root<UserProfile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.notEqual(root.get(EMAIL), SYSTEM_ADMINISTRATOR_EMAIL);
            }
        };
    }

    public static Specification<UserProfile> groupIdEquals(Integer roleId) {
        return new Specification<UserProfile>() {
            @Override
            public Predicate toPredicate(Root<UserProfile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<UserProfile, Group> groupJoin = root.join(GROUP);
                return criteriaBuilder.equal(groupJoin.get(ID), roleId);
            }
        };
    }

    public static Specification<UserProfile> roleIdEquals(Integer roleId) {
        return new Specification<UserProfile>() {
            @Override
            public Predicate toPredicate(Root<UserProfile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<UserProfile, Role> roleJoin = root.join(ROLE);
                return criteriaBuilder.equal(roleJoin.get(ID), roleId);
            }
        };
    }

}
