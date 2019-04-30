package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TWarehouseTkDetail;
import org.springframework.stereotype.Repository;

/**
 * Created by liyou on 2018/3/8.
 */
@Repository
public interface WarehouseTkDetailDao extends BaseRepository<TWarehouseTkDetail, Long> {
}
