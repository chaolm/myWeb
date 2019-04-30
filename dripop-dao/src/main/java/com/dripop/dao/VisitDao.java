package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TVisit;
import org.springframework.stereotype.Repository;

/**
 * 类名及用途
 *
 * @author dq
 * @date 2018/3/17 10:51
 */
@Repository
public interface VisitDao extends BaseRepository<TVisit, Long> {
}
