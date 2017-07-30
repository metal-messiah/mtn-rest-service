package com.mtn.repository;

import com.mtn.model.domain.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InteractionRepository extends JpaRepository<Interaction, Integer>, JpaSpecificationExecutor<Interaction> {

}

