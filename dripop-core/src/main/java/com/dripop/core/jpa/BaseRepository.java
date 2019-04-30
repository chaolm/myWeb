package com.dripop.core.jpa;

import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.QLBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liyou on 2017/6/19.
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>/*, QueryDslPredicateExecutor<T>*/ {
    public T insert(T t);

    public T update(T t);

    public void delete(ID id);

    public int deleteByJpql(QLBuilder ql);

    public T get(ID id);

    public int executeBySql(String sql, Object... params);

    public List findMapBySql(String sql, Object... params);

    public T findOneByJpql(QLBuilder ql);

    public <T> T findOneByJpql(String jpql, Object... params);

    public <T> T findOneBySql(String sql, Class<T> clz, Object... params);

    public List<T> findManyByJpql(QLBuilder ql);

    public <T> List<T> findManyByJpql(String jpql, Object... params);

    public <T> List<T> findManyBySql(String sql, Class<T> clz, Object... params);

    public <T> List<T> findLimitByJpql(QLBuilder ql, Integer limit);

    public <T> List<T> findLimitByJpql(String jpql, Integer limit, Object... params);

    public <T> List<T> findLimitBySql(String sql, Class<T> clz, Integer limit, Object... params);

    public <T> Pagination<T> findPageByJpql(QLBuilder ql, Pageable pageable);

    public <T> Pagination<T> findPageByJpql(String jpql, Pageable pageable, Object... params);

    public <T> Pagination<T> findPageBySql(String sql, Pageable pageable, Class<T> clz, Object... params);
}
