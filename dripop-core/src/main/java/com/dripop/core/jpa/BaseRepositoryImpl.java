package com.dripop.core.jpa;

import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.QLBuilder;
import com.dripop.core.util.BeanUtils;
import com.dripop.core.util.StringUtil;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

	private final EntityManager entityManager;
	private final JpaEntityInformation<T, ?> entityInformation;

	/**
	 * entityClass 实体类型
	 */
	private Class<?> entityClass;

	public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
		this(JpaEntityInformationSupport.getEntityInformation(domainClass, em), em);
	}

	public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
			EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityInformation = entityInformation;
		this.entityManager = entityManager;
//		System.out.println(entityInformation.getJavaType()+"---------------------"+entityInformation.getEntityName());
		this.entityClass = entityInformation.getJavaType();
	}

	private static final Logger logger = LoggerFactory.getLogger(BaseRepositoryImpl.class);

	public EntityManager getEntityManager()
	{
		return this.entityManager;
	}

	/* (non-Javadoc)
     * @see com.gxzy.framework.util.persistence.GenericDao#insert(java.lang.Object)
     */
	public T insert(T t) {
		// TODO Auto-generated method stub
		this.getEntityManager().persist(t);
		return t;
	}

	/* (non-Javadoc)
     * @see com.gxzy.framework.util.persistence.GenericDao#update(java.lang.Object)
     */
	public T update(T t) {
		// TODO Auto-generated method stub
		this.getEntityManager().merge(t);
		return t;
	}

	public void delete(ID id) {
		Object o = this.getEntityManager().find(this.entityClass, id);
		if(o == null){
			return;
		}
		this.getEntityManager().remove(o);
	}

	public int deleteByJpql(QLBuilder ql) {
		if(StringUtil.isBlank(ql.getMainClause())) {
			ql.setMainClause("DELETE FROM " + this.entityClass.getSimpleName() + " AS t");
		}
		return createQuery(ql.getFullQL(), ql.getParams()).executeUpdate();
	}

	public T get(ID id) {
		return (T)this.getEntityManager().find(this.entityClass, id);
	}

	public int executeBySql(String sql, Object... params) {
		return createNativeQuery(sql, Arrays.asList(params)).executeUpdate();
	}

	public List findMapBySql(String sql, Object... params) {
		Query query = createNativeQuery(sql, Arrays.asList(params));
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.getResultList();
	}

	public T findOneByJpql(QLBuilder ql) {
		try {
			if(StringUtil.isBlank(ql.getMainClause())) {
				ql.setMainClause("SELECT t FROM " + this.entityClass.getSimpleName() + " AS t");
			}
			return (T)createQuery(ql.getFullQL(), ql.getParams()).getSingleResult();
		}catch(NoResultException e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public <T> T findOneByJpql(String jpql, Object... params) {
		try {
			return (T)createQuery(jpql, Arrays.asList(params)).getSingleResult();
		}catch(NoResultException e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public <T> T findOneBySql(String sql, Class<T> clz, Object... params) {
		Object obj = null;
		try {
			Query query = createNativeQuery(sql, Arrays.asList(params));
			query.unwrap(SQLQuery.class).setResultTransformer(new EscColumnToBean(clz));
			obj = query.getSingleResult();
		}catch(NoResultException e) {
			logger.error(e.getMessage());
			return null;
		}
		try {
			return (T)obj;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public List<T> findManyByJpql(QLBuilder ql) {
		if(StringUtil.isBlank(ql.getMainClause())) {
			ql.setMainClause("SELECT t FROM " + this.entityClass.getSimpleName() + " AS t");
		}
		return createQuery(ql.getFullQL(), ql.getParams()).getResultList();
	}

	public <T> List<T> findManyByJpql(String jpql, Object... params) {
		return createQuery(jpql, Arrays.asList(params)).getResultList();
	}

	public <T> List<T> findManyBySql(String sql, Class<T> clz, Object... params) {
		Query query = createNativeQuery(sql, Arrays.asList(params));
		query.unwrap(SQLQuery.class).setResultTransformer(new EscColumnToBean(clz));
		return (List<T>)query.getResultList();
	}

	public <T> List<T> findLimitByJpql(QLBuilder ql, Integer limit) {
		if(StringUtil.isBlank(ql.getMainClause())) {
			ql.setMainClause("SELECT t FROM " + this.entityClass.getSimpleName() + " AS t");
		}
		return createQuery(ql.getFullQL(), ql.getParams()).setFirstResult(0).setMaxResults(limit).getResultList();
	}

	public <T> List<T> findLimitByJpql(String jpql, Integer limit, Object... params) {
		return createQuery(jpql, Arrays.asList(params)).setFirstResult(0).setMaxResults(limit).getResultList();
	}

	public <T> List<T> findLimitBySql(String sql, Class<T> clz, Integer limit, Object... params) {
		Query query = createNativeQuery(sql, Arrays.asList(params));
		query.unwrap(SQLQuery.class).setResultTransformer(new EscColumnToBean(clz));
		return (List<T>)query.setFirstResult(0).setMaxResults(limit).getResultList();
	}

	public <T> Pagination<T> findPageByJpql(QLBuilder ql, Pageable pageable) {
		if(StringUtil.isBlank(ql.getMainClause())) {
			ql.setMainClause("SELECT t FROM " + this.entityClass.getSimpleName() + " AS t");
		}
		Pagination<T> page = new Pagination<T>();
		page.setPage(pageable.getPageNumber());
		page.setLimit(pageable.getPageSize());
		Query query = this.createQuery(ql.getFullQL(), ql.getParams());
		Query countQuery = this.createQuery(this.getCountQL(ql.getFullQL(), pageable.getCountKeyField()), ql.getParams());

		query.setFirstResult(page.getStart());
		query.setMaxResults(page.getLimit());

		page.setItems(query.getResultList());
		page.setTotal( Long.parseLong(countQuery.getSingleResult().toString()));
		return page;
	}

	public <T> Pagination<T> findPageByJpql(String jpql, Pageable pageable, Object... params) {
		Pagination<T> page = new Pagination<T>();
		page.setPage(pageable.getPageNumber());
		page.setLimit(pageable.getPageSize());
		Query query = this.createQuery(jpql, Arrays.asList(params));
		Query countQuery = this.createQuery(this.getCountQL(jpql, pageable.getCountKeyField()), Arrays.asList(params));

		query.setFirstResult(page.getStart());
		query.setMaxResults(page.getLimit());

		page.setItems(query.getResultList());
		page.setTotal( Long.parseLong(countQuery.getSingleResult().toString()));
		return page;
	}

	public <T> Pagination<T> findPageBySql(String sql, Pageable pageable, Class<T> clz, Object... params) {
		Pagination<T> page = new Pagination<T>();
		page.setPage(pageable.getPageNumber());
		page.setLimit(pageable.getPageSize());
		Query query = this.createNativeQuery(sql, Arrays.asList(params));
		query.unwrap(SQLQuery.class).setResultTransformer(new EscColumnToBean(clz));
		Query countQuery = this.createNativeQuery(this.getCountSql(sql), Arrays.asList(params));

		if(pageable.getPageSize() != Pageable.NO_PAGE) {
			query.setFirstResult(page.getStart());
			query.setMaxResults(page.getLimit());
		}

		List<T> list = query.getResultList();
		page.setItems(list);
		page.setTotal( Long.parseLong(countQuery.getSingleResult().toString()));
		return page;
	}

//	public JPASQLQuery jpaSqlQuery() {
//		SQLTemplates dialect = new HSQLDBTemplates();
//		JPASQLQuery jpasqlQuery = new JPASQLQuery(this.getEntityManager(), dialect);
//		return jpasqlQuery;
//	}

	private String getCountSql(String ql) {
		StringBuffer countJpql = new StringBuffer("SELECT count(1) FROM (SELECT 1 ").append(ql.substring(ql.lastIndexOf("FROM"))).append(") t");
		return countJpql.toString();
	}

	private String getCountQL(String ql, String countFiled) {
		if(StringUtil.isBlank(countFiled)) {
			countFiled = "t";
		}
		StringBuffer countJpql = new StringBuffer("SELECT count("+countFiled+") ").append(ql.substring(ql.lastIndexOf("FROM")));
		return countJpql.toString();
	}

	private Query createQuery(String jpql, List<Object> params) {
		Query query = this.getEntityManager().createQuery(jpql);
		List<String> paramNames = this.resolveVarlible(jpql);
		this.setQueryParameterValues(query, paramNames, params);
		return query;
	}

	private Query createNativeQuery(String sql, List<Object> params) {
		Query query = this.getEntityManager().createNativeQuery(sql);
		List<String> paramNames = this.resolveVarlible(sql);
		this.setQueryParameterValues(query, paramNames, params);
		return query;
	}

	private void setQueryParameterValues(Query query, List<String> paramNames, List<Object> params) {
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(paramNames.get(i), params.get(i));
		}
	}

	/**
	 * 获取查询语句中的所有变量名，并以数组形式返回
	 *
	 * @param ql 查询语句，可以是jpql或者是sql
	 * @return 参数名列表，如果没有参数返回为null
	 */
	private List<String> resolveVarlible(String ql) {
		List<String> list = new ArrayList<String>();
		String regex = "\\:\\w+";

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(ql);

		String val = null;

		while (m.find()) {
			val = m.group();
			if(list.contains(val.substring(1))) {
				continue;
			}
			list.add(val.substring(1));
		}
		return list;
	}

	private String getEntityTableName() {
		Table table = this.entityClass.getAnnotation(Table.class);
		if(table!=null){
			return table.name();
		}
		return this.entityClass.getSimpleName();
	}

	protected void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	protected Class<?> getEntityClass() {
		return this.entityClass;
	}
}
