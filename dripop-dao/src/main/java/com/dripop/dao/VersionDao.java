package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TVersion;
import org.springframework.stereotype.Repository;

/**
 * Created by liyou on 2018/4/23.
 */
@Repository
public interface VersionDao extends BaseRepository<TVersion, Long> {
}
