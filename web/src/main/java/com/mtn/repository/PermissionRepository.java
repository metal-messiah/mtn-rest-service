package com.mtn.repository;

import com.mtn.model.domain.auth.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Allen on 5/6/2017.
 */
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}
