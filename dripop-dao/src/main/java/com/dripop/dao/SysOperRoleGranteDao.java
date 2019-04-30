package com.dripop.dao;

import com.dripop.core.jpa.BaseRepository;
import com.dripop.entity.TSysRoleGrante;
import org.springframework.stereotype.Repository;

@Repository
public interface SysOperRoleGranteDao extends BaseRepository<TSysRoleGrante, Long> {
}
