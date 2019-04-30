package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TFeedback;
import org.springframework.stereotype.Repository;

/**
 * 类名及用途
 *
 * @author dq
 * @date 2018/5/31 11:49
 */
@Repository
public interface FeedbackDao extends BaseRepository<TFeedback, Long> {
}
