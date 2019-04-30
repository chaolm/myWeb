package com.dripop.sys.service;

import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.entity.TSysmsg;
import com.dripop.sys.dto.SysmsgManageDto;

/**
 * 后台消息管理 service接口
 *
 * @author dq
 * @date 2018/3/19 10:26
 */

public interface SysmsgManageService {
    /**
     * 新增后台通知
     * @param sysmsgManageDto
     */
    void save(TSysmsg sysmsg);

    /**
     * 后台消息通知列表
     * @param sysmsgManageDto
     * @param pageable
     * @return
     */
    Pagination<SysmsgManageDto> sysmsgList(SysmsgManageDto sysmsgManageDto, Pageable pageable);

    /**
     * 根据id删除通知消息
     * @param id
     */
    void delete(Long id);

    /**
     * 根据id撤销通知消息
     * @param id
     */
    void repeal(Long id);
}
