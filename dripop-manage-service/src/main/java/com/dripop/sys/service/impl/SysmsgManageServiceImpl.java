package com.dripop.sys.service.impl;

import com.bean.IsRepeal;
import com.bean.MsgServiceType;
import com.dripop.core.bean.Filter;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.QLBuilder;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.DateUtil;
import com.dripop.core.util.StringUtil;
import com.dripop.dao.SysmsgDao;
import com.dripop.entity.TSysmsg;
import com.dripop.sys.dto.SysmsgManageDto;
import com.dripop.sys.service.SysmsgManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 后台消息管理 service实现类
 *
 * @author dq
 * @date 2018/3/19 10:27
 */
@Service
public class SysmsgManageServiceImpl implements SysmsgManageService {

    @Autowired
    private SysmsgDao sysmsgDao;

    @Override
    @Transactional
    public void save(TSysmsg sysmsg) {
        sysmsg.setServiceType(MsgServiceType.H5.getValue());
        sysmsg.setCreateTime(new Date());
        sysmsg.setStatus(0);
        sysmsg.setIsRepeal(IsRepeal.UN_REPEAL.getValue());
        sysmsgDao.insert(sysmsg);
    }

    @Override
    public Pagination<SysmsgManageDto> sysmsgList(SysmsgManageDto sysmsgManageDto, Pageable pageable) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT id,title,content,expire_time,start_time,link,is_repeal FROM t_sysmsg " +
                "WHERE service_type = :serviceType ");

        params.add(MsgServiceType.H5.getValue());
        if (sysmsgManageDto.getStartTime() != null) {
            sb.append("and start_time >= :startTime ");
            params.add(sysmsgManageDto.getStartTime());
        }
        if (sysmsgManageDto.getExpireTime() != null) {
            sb.append("and expire_time <= :endTime ");
            params.add(sysmsgManageDto.getExpireTime());
        }

        if (StringUtil.isNotBlank(sysmsgManageDto.getTitle())) {
            sb.append("and title like :title ");
            params.add("%" + sysmsgManageDto.getTitle() + "%");
        }

        sb.append("ORDER BY create_time DESC ");
        Pagination<SysmsgManageDto> pagination = sysmsgDao.findPageBySql(sb.toString(), pageable, SysmsgManageDto.class, params.toArray());
        if (pagination != null && pagination.getItems() != null && !pagination.getItems().isEmpty()) {
            boolean flag = false;
            for (SysmsgManageDto manageDto : pagination.getItems()) {
                flag = DateUtil.compareTime(manageDto.getStartTime());
                if (!flag) {
                    //未开始
                    manageDto.setType(1);
                } else {
                    if (IsRepeal.UN_REPEAL.getValue().equals(manageDto.getIsRepeal())) {
                        manageDto.setType(2);
                    } else if (IsRepeal.REPEAL.getValue().equals(manageDto.getIsRepeal())) {
                        manageDto.setType(3);
                    } else {
                        manageDto.setType(0);
                    }
                }
            }
        }
        return pagination;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        QLBuilder ql = new QLBuilder();
        ql.and(Filter.eq("id", id));
        TSysmsg tSysmsg = sysmsgDao.findOneByJpql(ql);
        if (DateUtil.compareTime(tSysmsg.getStartTime())) {
            throw new ServiceException("已经到开始时间的通知,不提供删除");
        }
        sysmsgDao.delete(id);
    }

    @Override
    @Transactional
    public void repeal(Long id) {
        QLBuilder ql = new QLBuilder();
        ql.and(Filter.eq("id", id));
        TSysmsg tSysmsg = sysmsgDao.findOneByJpql(ql);
        boolean flag = DateUtil.compareTime(tSysmsg.getStartTime());
        if (!flag) {
            throw new ServiceException("未到开始时间的通知,不提供撤销");
        }
        tSysmsg.setIsRepeal(IsRepeal.REPEAL.getValue());
        sysmsgDao.update(tSysmsg);
    }
}
