package com.mtn.controller;

import com.mtn.model.domain.UserBoundary;
import com.mtn.model.utils.TupleUtil;
import com.mtn.model.view.UserBoundaryView;
import com.mtn.service.UserBoundaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.util.List;

@RestController
@RequestMapping("/api/user-boundary")
public class UserBoundaryController extends CrudController<UserBoundary, UserBoundaryView> {

	private final EntityManager entityManager;

	@Autowired
	public UserBoundaryController(UserBoundaryService userBoundaryService, EntityManager entityManager) {
		super(userBoundaryService, UserBoundaryView::new);
		this.entityManager = entityManager;
	}

	@GetMapping
	public ResponseEntity findAllForUser(@RequestParam("user-id") Integer userId) {

		List<Tuple> resultList = this.entityManager
				.createNativeQuery(
						"SELECT ub.user_boundary_id as id, " + "ub.boundary_id as boundaryId, "
								+ "ub.boundary_name as boundaryName " + "from user_boundary ub "
								+ "where ub.user_profile_id = :userProfileId " + "and ub.deleted_date is null",
						Tuple.class)
				.setParameter("userProfileId", userId).getResultList();

		return ResponseEntity.ok(TupleUtil.toListOfMaps(resultList));
	}

}
