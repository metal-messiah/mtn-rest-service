package com.mtn.repository.specification.predicate;

import com.vividsolutions.jts.geom.Point;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.ParameterRegistry;
import org.hibernate.query.criteria.internal.Renderable;
import org.hibernate.query.criteria.internal.compile.RenderingContext;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.AbstractSimplePredicate;

import javax.persistence.criteria.Expression;
import java.io.Serializable;

public class WithinSphericalDistancePredicate extends AbstractSimplePredicate implements Serializable {
	private final Expression<Point> matchExpression;
	private final Expression<Point> circleCenter;
	private final Expression<Float> distance;

	public WithinSphericalDistancePredicate(CriteriaBuilderImpl criteriaBuilder, Expression<Point> matchExpression,
											Point circleCenter, Float distance) {
		super(criteriaBuilder);
		this.matchExpression = matchExpression;
		this.circleCenter = new LiteralExpression<>(criteriaBuilder, circleCenter);
		this.distance = new LiteralExpression<>(criteriaBuilder, distance);
	}

	public void registerParameters(ParameterRegistry registry) {
		// Nothing to register
	}

	@Override
	public String render(boolean isNegated, RenderingContext renderingContext) {
		String sign = isNegated ? ">" : "<=";
		return " ST_Distance_Sphere(" + ((Renderable) this.matchExpression).render(renderingContext) +
				", " + ((Renderable) this.circleCenter).render(renderingContext) + ") " + sign + " " + ((Renderable) this.distance).render(renderingContext);
	}
}
