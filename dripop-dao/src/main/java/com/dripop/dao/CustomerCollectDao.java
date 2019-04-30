package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TCustomerCollect;
import org.springframework.stereotype.Repository;

/**
 * Created by liyou on 2017/12/27.
 */
@Repository
public interface CustomerCollectDao extends BaseRepository<TCustomerCollect, Long> {
}
