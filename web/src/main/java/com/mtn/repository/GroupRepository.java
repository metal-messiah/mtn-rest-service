package com.mtn.repository;

import com.mtn.model.domain.auth.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Allen on 5/6/2017.
 */
public interface GroupRepository extends JpaRepository<Group, Integer>, JpaSpecificationExecutor<Group> {
}
