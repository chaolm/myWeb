package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TAccount;
import org.springframework.stereotype.Repository;

/**
 * Created by clm
 */
@Repository
public interface AccountDao extends BaseRepository<TAccount, Long> {
}
