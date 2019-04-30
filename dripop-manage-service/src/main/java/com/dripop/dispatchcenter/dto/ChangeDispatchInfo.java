package com.dripop.dispatchcenter.dto;

import java.io.Serializable;

public class ChangeDispatchInfo implements Serializable{

    private Long orgId;
    private Long spuId;
    private Integer num;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
