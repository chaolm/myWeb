package com.dripop.huodong.impl;

import com.dripop.core.exception.ServiceException;
import com.dripop.dao.SysOrgDao;
import com.dripop.huodong.HuodongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by liyou on 2018/4/27.
 */
@Service
public class HuodongServiceImpl implements HuodongService {

    @Autowired
    private SysOrgDao sysOrgDao;

    @Transactional
    public void yuyue(String phone, String address) {
        sysOrgDao.executeBySql("insert into t_huodong (id, phone, address, create_time) values(null, :phone, :address, :date)", phone, address, new Date());
    }

    @Override
    @Transactional
    public void qiusai(String phone, String openId, String gj, String yj) {
        String sql = "select count(id) from t_huodong t where t.type = 11 and (t.wechat_open_id = :openId or t.phone = :phone)";
        Long count = sysOrgDao.findOneBySql(sql, Long.class, openId, phone);
        if(count > 0) {
            throw new ServiceException("该微信或手机号已参与过活动");
        }
        String answer = "冠军:"+gj+",亚军:"+yj;
        sysOrgDao.executeBySql("insert into t_huodong (id, phone, wechat_open_id, answer, type, create_time) values(null, :phone, :openid, :answer, :type, :date)", phone, openId, answer, 11, new Date());
    }

    @Override
    @Transactional
    public void activeYear(String phone, String openId) {
        String sql = "select count(id) from t_huodong t where t.type = 12 and (t.wechat_open_id = :openId or t.phone = :phone)";
        Long count = sysOrgDao.findOneBySql(sql, Long.class, openId, phone);
        if(count > 0) {
            throw new ServiceException("该微信或手机号已参与过活动");
        }
        sysOrgDao.executeBySql("insert into t_huodong (id, phone, wechat_open_id,  type, create_time) values(null, :phone, :openid,  :type, :date)", phone, openId,  12, new Date());
    }
}
