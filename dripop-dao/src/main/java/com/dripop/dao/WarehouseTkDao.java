package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TWarehouseTk;
import org.springframework.stereotype.Repository;

/**
 * Created by liyou on 2018/3/8.
 */
@Repository
public interface WarehouseTkDao extends BaseRepository<TWarehouseTk, Long> {
}
