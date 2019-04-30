package com.dripop.comment.dto;

import java.io.Serializable;

public class CommentSearchReq implements Serializable {

    //用户账号
    private String  phoneNo;
    //评价状态
    private Integer status;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
