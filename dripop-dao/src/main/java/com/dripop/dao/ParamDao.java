package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TParam;
import org.springframework.stereotype.Repository;

/**
 * Created by liyou on 2017/6/15.
 */
@Repository
public interface ParamDao extends BaseRepository<TParam, Long> {
}
