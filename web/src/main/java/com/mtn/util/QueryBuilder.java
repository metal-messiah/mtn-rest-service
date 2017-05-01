package com.mtn.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 4/30/2017.
 */
public abstract class QueryBuilder {

    protected StringBuilder query;
    protected String sortColumn;
    protected Map<String, Object> params = new HashMap<>();
    protected List<String> clauses = new ArrayList<>();

    public QueryBuilder(String query, String sortColumn) {
        this.query = new StringBuilder(query);
        this.sortColumn = sortColumn;
    }

    protected String prepareQuery() {
        for (int i = 0; i < clauses.size(); i++) {
            String clause = clauses.get(i);

            //Append clause to query
            query.append(clause);

            //Append AND if not last clause in list
            if (i < clauses.size() - 1) {
                query.append("AND");
            }
        }

        //This will likely need to become dynamic
        query.append(String.format("ORDER BY %s DESC", sortColumn));

        return query.toString();
    }

    public String getQuery() {
        return query.toString();
    }

    public Map<String, Object> getParams() {
        return params;
    }
}
