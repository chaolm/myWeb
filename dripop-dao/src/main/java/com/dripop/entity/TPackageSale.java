package com.dripop.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by shery on 2017/8/1.
 */
@javax.persistence.Entity
@Table(name = "t_package_sale")
public class TPackageSale implements java.io.Serializable{
    public interface TYPE{
        public static final int RECOMMEND = 1;//推荐套餐
        public static final int COMBINE = 2;  //组合套餐
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "main_online_id")
    private Long mainOnlineId;

    @Column(name = "bind_online_id")
    private Long bindOnlineId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMainOnlineId() {
        return mainOnlineId;
    }

    public void setMainOnlineId(Long mainOnlineId) {
        this.mainOnlineId = mainOnlineId;
    }

    public Long getBindOnlineId() {
        return bindOnlineId;
    }

    public void setBindOnlineId(Long bindOnlineId) {
        this.bindOnlineId = bindOnlineId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
}
