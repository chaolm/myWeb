package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TGiftPromotion;
import org.springframework.stereotype.Repository;

/**
 * Created by liyou on 2018/2/5.
 */
@Repository
public interface GiftPromotionDao extends BaseRepository<TGiftPromotion, Long> {
}
