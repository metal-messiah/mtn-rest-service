package com.mtn.repository.specification;

import com.mtn.model.domain.Group;
import com.mtn.model.domain.Group_;
import org.springframework.data.jpa.domain.Specification;

public class GroupSpecifications extends AuditingEntitySpecifications {

    public static Specification<Group> displayNameContains(String value) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(Group_.displayName)), String.format("%%%s%%", value.toLowerCase()));
    }

}
