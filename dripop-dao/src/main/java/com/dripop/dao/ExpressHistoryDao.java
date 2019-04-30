package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TExpressHistory;
import org.springframework.stereotype.Repository;

/**
 * Created by liyou on 2018/3/16.
 */
@Repository
public interface ExpressHistoryDao extends BaseRepository<TExpressHistory, Long> {
}
