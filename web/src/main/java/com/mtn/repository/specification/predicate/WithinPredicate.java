package com.mtn.repository.specification.predicate;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.ParameterRegistry;
import org.hibernate.query.criteria.internal.Renderable;
import org.hibernate.query.criteria.internal.compile.RenderingContext;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.AbstractSimplePredicate;

import javax.persistence.criteria.Expression;
import java.io.Serializable;

public class WithinPredicate extends AbstractSimplePredicate implements Serializable {
	private final Expression<Point> matchExpression;
	private final Expression<Geometry> area;

	public WithinPredicate(CriteriaBuilderImpl criteriaBuilder, Expression<Point> matchExpression, Geometry area) {
		super(criteriaBuilder);
		this.matchExpression = matchExpression;
		this.area =  new LiteralExpression<>(criteriaBuilder, area);
	}

	public void registerParameters(ParameterRegistry registry) {
		// Nothing to register
	}

	@Override
	public String render(boolean isNegated, RenderingContext renderingContext) {
		String tf = isNegated ? "false" : "true";
		return " ST_within(" + ((Renderable) this.matchExpression).render(renderingContext) +
				", " + ((Renderable) this.area).render(renderingContext) + ") = " + tf;
	}
}
