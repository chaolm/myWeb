package com.dripop.helpFeedback.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.bean.FeedbackType;
import com.bean.SourceChannel;

import java.io.Serializable;
import java.util.Date;

/**
 * 反馈
 *
 * @author dq
 * @date 2018/5/31 17:05
 */

public class FeedbackDto implements Serializable{

    /*反馈ID*/
    private Long id;

    /*反馈时间*/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /*反馈类型 1提个意见 2功能意见 3界面意见 4其他问题*/
    private Integer type;

    /*反馈类型 1提个意见 2功能意见 3界面意见 4其他问题*/
    private String typeStr;

    /*反馈图片*/
    private String reserve1;

    /*反馈图片*/
    private String reserve2;

    /*反馈图片*/
    private String reserve3;

    /*反馈内容*/
    private String content;

    /*联系人号码*/
    private String phoneNo;

    /*反馈平台*/
    private Integer channel;

    private String channelStr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeStr() {
        return FeedbackType.getName(type);
    }

    public String getReserve1() {
        return reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1;
    }

    public String getReserve2() {
        return reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2;
    }

    public String getReserve3() {
        return reserve3;
    }

    public void setReserve3(String reserve3) {
        this.reserve3 = reserve3;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getChannelStr() {
        return SourceChannel.getName(channel);
    }
}
