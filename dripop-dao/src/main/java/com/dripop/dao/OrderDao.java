package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TOrder;
import org.springframework.stereotype.Repository;

/**
 * Created by liyou on 2018/2/28.
 */
@Repository
public interface OrderDao extends BaseRepository<TOrder, Long> {
}
