package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TSysmsg;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by liyou on 2018/3/15.
 */
@Repository
public interface SysmsgDao extends BaseRepository<TSysmsg, Long> {
}
