package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TParamData;
import org.springframework.stereotype.Repository;

/**
 * Created by liyou on 2017/6/15.
 */
@Repository
public interface ParamDataDao extends BaseRepository<TParamData, Long> {
}
