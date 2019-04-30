package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TGoodsNotice;
import org.springframework.stereotype.Repository;

/**
 * Created by liyou on 2018/3/24.
 */
@Repository
public interface GoodsNoticeDao extends BaseRepository<TGoodsNotice, Long> {
}
