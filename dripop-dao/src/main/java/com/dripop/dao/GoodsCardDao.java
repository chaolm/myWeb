package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TGoodsCard;
import org.springframework.stereotype.Repository;

/**
 * Created by liyou on 2018/2/2.
 */
@Repository
public interface GoodsCardDao extends BaseRepository<TGoodsCard, Long> {
}
