package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by liyou on 2018/2/2.
 */
@Repository
public interface CardDao extends BaseRepository<TCard, Long> {
}
