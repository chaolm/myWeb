package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TStaticData;
import org.springframework.stereotype.Repository;

/**
 * Created by liyou on 2017/9/21.
 */
@Repository
public interface StaticDataDao extends BaseRepository<TStaticData, Integer> {
}
