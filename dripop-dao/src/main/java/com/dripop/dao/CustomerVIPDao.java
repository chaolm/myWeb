package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TCustomerVip;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerVIPDao extends BaseRepository<TCustomerVip, Long> {
}
