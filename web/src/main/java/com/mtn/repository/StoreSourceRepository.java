package com.mtn.repository;

import com.mtn.model.domain.StoreSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface StoreSourceRepository extends JpaRepository<StoreSource, Integer>, JpaSpecificationExecutor<StoreSource> {

    @Query("SELECT max(s.sourceEditedDate) FROM StoreSource s")
    LocalDateTime getMaxSourceEditedDate();

}
