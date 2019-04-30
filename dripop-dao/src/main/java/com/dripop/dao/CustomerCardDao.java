package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TCustomerCard;
import org.springframework.stereotype.Repository;

/**
 * Created by liyou on 2018/2/2.
 */
@Repository
public interface CustomerCardDao extends BaseRepository<TCustomerCard, Long> {
}
