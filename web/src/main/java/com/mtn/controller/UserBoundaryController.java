package com.mtn.controller;

import com.mtn.model.domain.UserBoundary;
import com.mtn.model.simpleView.SimpleUserBoundaryView;
import com.mtn.model.view.UserBoundaryView;
import com.mtn.service.UserBoundaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import java.util.List;

@RestController
@RequestMapping("/api/user-boundary")
public class UserBoundaryController extends CrudController<UserBoundary, UserBoundaryView> {

	private final EntityManager entityManager;

	@Autowired
	public UserBoundaryController(UserBoundaryService userBoundaryService,
								  EntityManager entityManager) {
		super(userBoundaryService, UserBoundaryView::new);
		this.entityManager = entityManager;
	}

	@GetMapping
	public ResponseEntity findAllForUser(@RequestParam("user-id") Integer userId) {
		Query query = this.entityManager.createNamedQuery("getAllUserBoundariesForUser")
				.setParameter("userProfileId", userId);

//		Query query = this.entityManager.createNativeQuery("SELECT ub.user_boundary_id, ub.boundary_id, ub.boundary_name " +
//				"from user_boundary ub " +
//				"where ub.user_profile_id = :userProfileId " +
//				"and ub.deleted_date is null", Tuple.class)
//				.setParameter("userProfileId", userId);

		List<Tuple> resultList = query.getResultList();

		return ResponseEntity.ok(resultList);
	}

}
