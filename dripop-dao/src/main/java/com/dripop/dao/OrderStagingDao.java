package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TOrderStaging;
import org.springframework.stereotype.Repository;

/**
 * Created by liyou on 2018/3/29.
 */
@Repository
public interface OrderStagingDao extends BaseRepository<TOrderStaging, Long> {
}
