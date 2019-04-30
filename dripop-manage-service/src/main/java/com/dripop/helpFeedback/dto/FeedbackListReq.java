package com.dripop.helpFeedback.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 反馈列表请求
 *
 * @author dq
 * @date 2018/5/31 17:02
 */

public class FeedbackListReq implements Serializable{

    private Integer channel;

    private Integer type;

    private Date startTime;

    private Date endTime;

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
