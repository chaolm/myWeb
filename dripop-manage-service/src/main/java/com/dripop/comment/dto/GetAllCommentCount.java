package com.dripop.comment.dto;

import java.io.Serializable;

public class GetAllCommentCount implements Serializable {
    //评论总数数量
    private Long allCount;
    //待审核数数量
    private Long waitCount;
    //通过审核数量
    private Long passCount;
    //拒绝通过审核数量
    private Long refuseCount;

    public Long getAllCount() {
        return allCount;
    }

    public void setAllCount(Long allCount) {
        this.allCount = allCount;
    }

    public Long getWaitCount() {
        return waitCount;
    }

    public void setWaitCount(Long waitCount) {
        this.waitCount = waitCount;
    }

    public Long getPassCount() {
        return passCount;
    }

    public void setPassCount(Long passCount) {
        this.passCount = passCount;
    }

    public Long getRefuseCount() {
        return refuseCount;
    }

    public void setRefuseCount(Long refuseCount) {
        this.refuseCount = refuseCount;
    }
}
