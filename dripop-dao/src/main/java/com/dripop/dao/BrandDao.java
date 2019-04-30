package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TBrand;
import org.springframework.stereotype.Repository;

/**
 * Created by liyou on 2018/1/10.
 */
@Repository
public interface BrandDao extends BaseRepository<TBrand, Long> {
}
