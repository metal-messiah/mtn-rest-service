package com.mtn.repository.specification;

import com.mtn.constant.StoreType;
import com.mtn.model.domain.Project;
import com.mtn.model.domain.Site;
import com.mtn.model.domain.Store;
import com.mtn.model.domain.UserProfile;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * Created by Allen on 4/26/2017.
 */
public class StoreSpecifications extends AuditingEntitySpecifications {

    private static final String ID = "id";
    private static final String SITE = "site";
    private static final String PROJECT = "project";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String STORE_TYPE = "storeType";
    private static final String ASSIGNEE = "assignee";

    public static Specification<Store> siteIdEquals(Integer id) {
        return new Specification<Store>() {
            @Override
            public Predicate toPredicate(Root<Store> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<Store, Site> storeSiteJoin = root.join(SITE);
                return criteriaBuilder.equal(storeSiteJoin.get(ID), id);
            }
        };
    }

    public static Specification<Store> projectIdEquals(Integer projectId) {
        return new Specification<Store>() {
            @Override
            public Predicate toPredicate(Root<Store> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<Store, Project> storeProjectJoin = root.join(PROJECT);
                return criteriaBuilder.equal(storeProjectJoin.get(ID), projectId);
            }
        };
    }

    public static Specification<Store> ofTypes(List<StoreType> storeTypes) {
        return new Specification<Store>() {
            @Override
            public Predicate toPredicate(Root<Store> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return root.get(STORE_TYPE).in(storeTypes);
//                StoreType requestedStoreType = StoreType.valueOf(storeType);
//                return criteriaBuilder.in(, storeTypes);
            }
        };
    }

    public static Specification<Store> assignedTo(Integer assigneeId) {
        return new Specification<Store>() {
            @Override
            public Predicate toPredicate(Root<Store> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<Store, Site> storeSite = root.join(SITE);
                Join<Site, UserProfile> siteAssignee = storeSite.join(ASSIGNEE);
                return criteriaBuilder.equal(siteAssignee.get(ID), assigneeId);
            }
        };
    }

    public static Specification<Store> withinBoundingBox(Float north, Float south, Float east, Float west) {
        return new Specification<Store>() {
            @Override
            public Predicate toPredicate(Root<Store> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<Store, Site> storeSiteJoin = root.join(SITE);
                Predicate northBound = criteriaBuilder.lessThanOrEqualTo(storeSiteJoin.get(LATITUDE), north);
                Predicate southBound = criteriaBuilder.greaterThanOrEqualTo(storeSiteJoin.get(LATITUDE), south);
                Predicate westBound = criteriaBuilder.greaterThanOrEqualTo(storeSiteJoin.get(LONGITUDE), west);
                Predicate eastBound = criteriaBuilder.lessThanOrEqualTo(storeSiteJoin.get(LONGITUDE), east);
                return criteriaBuilder.and(northBound, southBound, westBound, eastBound);
            }
        };
    }

    public static Specification<Store> withinBoundingBoxOrAssignedTo(Float north, Float south, Float east, Float west, Integer assigneeId) {
        return new Specification<Store>() {
            @Override
            public Predicate toPredicate(Root<Store> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Store, Site> storeSiteJoin = root.join(SITE);
                Join<Site, UserProfile> siteAssignee = storeSiteJoin.join(ASSIGNEE, JoinType.LEFT);
                Predicate northBound = cb.lessThanOrEqualTo(storeSiteJoin.get(LATITUDE), north);
                Predicate southBound = cb.greaterThanOrEqualTo(storeSiteJoin.get(LATITUDE), south);
                Predicate westBound = cb.greaterThanOrEqualTo(storeSiteJoin.get(LONGITUDE), west);
                Predicate eastBound = cb.lessThanOrEqualTo(storeSiteJoin.get(LONGITUDE), east);
                Predicate inBounds = cb.and(northBound, southBound, westBound, eastBound);
                Predicate assignedTo = cb.equal(siteAssignee.get(ID), assigneeId);
                return cb.or(assignedTo, inBounds);
            }
        };
    }
}
