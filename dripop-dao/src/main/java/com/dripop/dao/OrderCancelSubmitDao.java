package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TOrderCancelSubmit;
import org.springframework.stereotype.Repository;

/**
 * Created by liyou on 2018/3/8.
 */
@Repository
public interface OrderCancelSubmitDao extends BaseRepository<TOrderCancelSubmit, Long> {
}
