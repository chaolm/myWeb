package com.dripop.sys.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.bean.IsPick;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liyou on 2017/12/12.
 */
public class OpStoreSearchDto implements Serializable {

    private Long orgId;

    private String name;

    private Integer status;

    private String statusText;

    private Long personNum;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String address;

    private Integer isPick;

    private String isPickStr;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusText() {
        if(status == null) {
            return "";
        }else if(status == 1) {
            return "营业中";
        }else if(status == 2) {
            return "已停业";
        }
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Long getPersonNum() {
        return personNum;
    }

    public void setPersonNum(Long personNum) {
        this.personNum = personNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIsPick() {
        return isPick;
    }

    public void setIsPick(Integer isPick) {
        this.isPick = isPick;
    }

    public String getIsPickStr() {
        return IsPick.getName(isPick);
    }
}
