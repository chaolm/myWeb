package com.dripop.core.bean;

import org.springframework.util.CollectionUtils;

import java.util.*;

public class QLBuilder {

	private StringBuffer jpql;

	private String mainClause;

	private List<Filter> params = new ArrayList<Filter>();

	private Boolean isWhere = false;

	public QLBuilder() {
		clear();
	}

	public void and(Filter filter) {
		jpql.append(" and ").append(getQlByFilter(filter, this.params.size()));
		this.params.add(filter);
		isWhere = true;
	}

	public void and(Filter... filters) {
		jpql.append(" and (");
		for (Filter filter : filters) {
			jpql.append(getQlByFilter(filter, this.params.size())).append(" or ");
			this.params.add(filter);
		}
		jpql.delete(jpql.length() - 4, jpql.length());
		jpql.append(")");
		isWhere = true;
	}

	public void or(Filter filter) {
		jpql.append(" or ").append(getQlByFilter(filter, this.params.size()));
		this.params.add(filter);
		isWhere = true;
	}

	public void or(Filter... filters) {
		jpql.append(" or (");
		for (Filter filter : filters) {
			jpql.append(getQlByFilter(filter, this.params.size())).append(" and ");
			this.params.add(filter);
		}
		jpql.delete(jpql.length() - 5, jpql.length());
		jpql.append(")");
		isWhere = true;
	}

	public void add(String ql) {
		jpql.append(" ").append(ql);
	}

	public void where(String ql) {
		jpql.append(" ").append(ql);
		isWhere = true;
	}

	public void clear() {
		this.jpql = new StringBuffer();
		this.params = new ArrayList<Filter>();
		this.mainClause = "";
		this.isWhere = false;
	}

	public String getFullQL() {
		StringBuffer sb = new StringBuffer(jpql);
		if(isWhere && this.getMainClause().toLowerCase().indexOf(" where ") <= 0) {
			if(sb.toString().startsWith(" and ")) {
				sb.delete(0, 5);
			}else if(sb.toString().startsWith(" or ")) {
				sb.delete(0, 4);
			}
			if(sb.length() > 0) {
				sb.insert(0, " where ");
			}
		}
		sb.insert(0, this.getMainClause());
		return sb.toString();
	}

	private String getQlByFilter(Filter filter, int i) {
		StringBuffer sb = new StringBuffer();
		if(filter.getOperator().equals(Operator.eq)) {
			sb.append(filter.getProperty()).append(" = ").append(":param").append(i);
		}else if(filter.getOperator().equals(Operator.ne)) {
			sb.append(filter.getProperty()).append(" != ").append(":param").append(i);
		}else if(filter.getOperator().equals(Operator.lt)) {
			sb.append(filter.getProperty()).append(" < ").append(":param").append(i);
		}else if(filter.getOperator().equals(Operator.le)) {
			sb.append(filter.getProperty()).append(" <= ").append(":param").append(i);
		}else if(filter.getOperator().equals(Operator.gt)) {
			sb.append(filter.getProperty()).append(" > ").append(":param").append(i);
		}else if(filter.getOperator().equals(Operator.ge)) {
			sb.append(filter.getProperty()).append(" >= ").append(":param").append(i);
		}else if(filter.getOperator().equals(Operator.isNull)) {
			sb.append(filter.getProperty()).append(" is null");
		}else if(filter.getOperator().equals(Operator.isNotNull)) {
			sb.append(filter.getProperty()).append(" is not null");
		}else if(filter.getOperator().equals(Operator.like)) {
			sb.append(filter.getProperty()).append(" like ").append(":param").append(i);
		}else if(filter.getOperator().equals(Operator.in)) {
			sb.append(filter.getProperty()).append(" in (").append(":param").append(i).append(")");
		}
		return sb.toString();
	}

	public List<Object> getParams() {
		List<Object> list = new ArrayList<Object>();
		for (Filter filter : this.params) {
			if(filter.getValue() == null) {
				continue;
			}
			list.add(filter.getValue());
		}
		return list;
	}

	public String getMainClause() {
		return mainClause;
	}

	public void setMainClause(String mainClause) {
		this.mainClause = mainClause;
	}

}
