package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;

import com.dripop.entity.TGoodsImeiLog;
import org.springframework.stereotype.Repository;
/**
 * Created by chaoleiming on 2018/3/18.
 */
@Repository
public interface GoodsImeiLogDao extends BaseRepository<TGoodsImeiLog, Long> {
}
