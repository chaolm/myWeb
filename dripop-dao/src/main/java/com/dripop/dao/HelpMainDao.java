package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.THelpMain;
import org.springframework.stereotype.Repository;

/**
 * 类名及用途
 *
 * @author dq
 * @date 2018/5/31 11:50
 */
@Repository
public interface HelpMainDao extends BaseRepository<THelpMain, Long> {
}
