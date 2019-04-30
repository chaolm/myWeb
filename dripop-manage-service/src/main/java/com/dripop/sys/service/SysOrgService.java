
package com.dripop.sys.service;

import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.entity.TSysOrg;
import com.dripop.sys.dto.OpStoreSearchDto;
import com.dripop.sys.dto.OpStoreSearchReq;

import java.util.List;

public interface SysOrgService {

	TSysOrg findById(Long id);

	void update(TSysOrg sysOrg);

	Pagination<OpStoreSearchDto> pageStore(OpStoreSearchReq reqDto, Pageable pageable);

	void businessSetting(Long orgId, Integer status);

	void deleteStore(Long orgId);

	void addStore(TSysOrg sysOrg, String logoStr,String password);

	void updateStore(TSysOrg sysOrg, String logoStr,String password);

	List<OpStoreSearchDto> listStore(OpStoreSearchReq reqDto);

	TSysOrg storeDetail(Long orgId);
}