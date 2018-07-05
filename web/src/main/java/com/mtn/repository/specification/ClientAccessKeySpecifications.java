package com.mtn.repository.specification;

import com.mtn.model.domain.ClientAccessKey;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Tyler on 2/14/2018.
 */
public class ClientAccessKeySpecifications {

    private static final String DELETED_DATE = "deletedDate";
    private static final String ID = "id";
    private static final String CLIENT_UNIQUE_IDENTIFIER = "clientUniqueIdentifier";
    private static final String ACCESS_KEY = "accessKey";

    public static Specification<ClientAccessKey> idEquals(Integer id) {
        return new Specification<ClientAccessKey>() {
            @Override
            public Predicate toPredicate(Root<ClientAccessKey> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(ID), id);
            }
        };
    }

    public static Specification<ClientAccessKey> isNotDeleted() {
        return new Specification<ClientAccessKey>() {
            @Override
            public Predicate toPredicate(Root<ClientAccessKey> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(DELETED_DATE));
            }
        };
    }

    public static Specification<ClientAccessKey> clientUniqueIdentifierEquals(String clientUniqueIdentifier) {
        return new Specification<ClientAccessKey>() {
            @Override
            public Predicate toPredicate(Root<ClientAccessKey> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(CLIENT_UNIQUE_IDENTIFIER), clientUniqueIdentifier);
            }
        };
    }

    public static Specification<ClientAccessKey> accessKeyEquals(String accessKey) {
        return new Specification<ClientAccessKey>() {
            @Override
            public Predicate toPredicate(Root<ClientAccessKey> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(ACCESS_KEY), accessKey);
            }
        };
    }
}