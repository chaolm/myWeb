package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.THelpResolved;
import org.springframework.stereotype.Repository;

/**
 * 类名及用途
 *
 * @author dq
 * @date 2018/6/8 18:35
 */
@Repository
public interface HelpResolvedDao extends BaseRepository<THelpResolved, Long> {
}
