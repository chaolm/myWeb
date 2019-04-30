package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TGift;
import com.dripop.entity.TGoodsGift;
import org.springframework.stereotype.Repository;

/**
 * Created by liyou on 2018/2/5.
 */
@Repository
public interface GiftDao extends BaseRepository<TGift, Long> {
}
