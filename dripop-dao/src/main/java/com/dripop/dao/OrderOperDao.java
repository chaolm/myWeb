package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TOrderOper;
import org.springframework.stereotype.Repository;

/**
 * 订单操作
 *
 * @author dq
 * @date 2018/3/23 12:11
 */
@Repository
public interface OrderOperDao extends BaseRepository<TOrderOper, Long> {
}
