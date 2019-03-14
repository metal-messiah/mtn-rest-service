package com.mtn.repository.specification;

import com.mtn.model.domain.UserProfile;
import com.mtn.model.domain.UserProfile_;
import org.springframework.data.jpa.domain.Specification;

public class UserProfileSpecifications extends AuditingEntitySpecifications {

    private static final String SYSTEM_ADMINISTRATOR_EMAIL = "system.administrator@mtnra.com";

    public static Specification<UserProfile> emailContains(String value) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(UserProfile_.email)), String.format("%%%s%%", value.toLowerCase()));
    }

    public static Specification<UserProfile> emailEqualsIgnoreCase(String value) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.lower(root.get(UserProfile_.email)), value.toLowerCase());
    }

    public static Specification<UserProfile> firstNameContains(String value) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(UserProfile_.firstName)), String.format("%%%s%%", value.toLowerCase()));
    }

    public static Specification<UserProfile> lastNameContains(String value) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(UserProfile_.lastName)), String.format("%%%s%%", value.toLowerCase()));
    }

    public static Specification<UserProfile> isNotSystemAdministrator() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.notEqual(root.get(UserProfile_.email), SYSTEM_ADMINISTRATOR_EMAIL);
    }

    public static Specification<UserProfile> groupIdEquals(Integer roleId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal( root.join(UserProfile_.group).get(UserProfile_.id), roleId);
    }

    public static Specification<UserProfile> roleIdEquals(Integer roleId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join(UserProfile_.role).get(UserProfile_.id), roleId);
    }

}
