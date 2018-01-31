package com.mtn.repository;

import com.mtn.model.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Allen on 5/6/2017.
 */
public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {

    Role findOneByDisplayNameIgnoreCase(String displayName);
}
