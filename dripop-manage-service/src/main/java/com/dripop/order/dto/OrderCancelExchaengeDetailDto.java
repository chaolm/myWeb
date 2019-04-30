package com.dripop.order.dto;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 类名及用途
 *
 * @author dq
 * @date 2018/4/16 9:20
 */

public class OrderCancelExchaengeDetailDto implements Serializable {
    /**
     * 售后单id
     */
    private Long id;

    /**
     * 关联订单id
     */
    private Long orderId;

    /**
     * 售后单服务号
     */
    private String cancelOrderNo;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 后台审核处理完成时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date opAuditTime;

    /**
     * 后台审核处理时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /**
     * 用户取消售后单申请服务时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date requestCancelTime;

    /**
     * 审核拒绝原因或者换货拒绝原因
     */
    private String refuseReason;

    /**
     * 售后单服务类型
     */
    private Integer serviceType;

    /**
     * 售后单联系方式
     */
    private String contactPhone;

    /**
     * 售后单是否解封
     */
    private Integer isOpen;

    /**
     * 售后单是否检测
     */
    private Integer isCheck;

    /**
     * 售后单退款原因
     */
    private String cancelReason;

    /**
     * 售后单退款说明
     */
    private String cancelDesc;

    public Integer getTotalRefund() {
        return totalRefund;
    }

    public void setTotalRefund(Integer totalRefund) {
        this.totalRefund = totalRefund;
    }

    /**
     * 售后单退款说明
     */
    private Integer totalRefund;

    /**
     * 订单详情
     */
    private List details;

    private String reserve1;

    private String reserve2;

    private String reserve3;

    private String reserve4;

    private String reserve5;

    private String reserve6;

    private List<String> imgUrls;//ExpressMsgDto

    private List<ExpressMsgDto> expressMsgDtos;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<ExpressMsgDto> getExpressMsgDtos() {
        return expressMsgDtos;
    }

    public void setExpressMsgDtos(List<ExpressMsgDto> expressMsgDtos) {
        this.expressMsgDtos = expressMsgDtos;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public Date getOpAuditTime() {
        return opAuditTime;
    }

    public void setOpAuditTime(Date opAuditTime) {
        this.opAuditTime = opAuditTime;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Date getRequestCancelTime() {
        return requestCancelTime;
    }

    public void setRequestCancelTime(Date requestCancelTime) {
        this.requestCancelTime = requestCancelTime;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getCancelDesc() {
        return cancelDesc;
    }

    public void setCancelDesc(String cancelDesc) {
        this.cancelDesc = cancelDesc;
    }

    public List<String> getImgUrls() {

        imgUrls = new ArrayList<String>();

        if (StringUtils.isNotEmpty(reserve1)) {
            imgUrls.add(reserve1);
        }
        if (StringUtils.isNotEmpty(reserve2)) {
            imgUrls.add(reserve2);
        }
        if (StringUtils.isNotEmpty(reserve3)) {
            imgUrls.add(reserve3);
        }
        if (StringUtils.isNotEmpty(reserve4)) {
            imgUrls.add(reserve4);
        }
        if (StringUtils.isNotEmpty(reserve5)) {
            imgUrls.add(reserve5);
        }
        if (StringUtils.isNotEmpty(reserve6)) {
            imgUrls.add(reserve6);
        }
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
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

    public String getReserve4() {
        return reserve4;
    }

    public void setReserve4(String reserve4) {
        this.reserve4 = reserve4;
    }

    public String getReserve5() {
        return reserve5;
    }

    public void setReserve5(String reserve5) {
        this.reserve5 = reserve5;
    }

    public String getReserve6() {
        return reserve6;
    }

    public void setReserve6(String reserve6) {
        this.reserve6 = reserve6;
    }

    public Integer getOpStatus() {

        return opStatus;
    }

    public void setOpStatus(Integer opStatus) {
        this.opStatus = opStatus;
    }

    /**
     * 后台审核状态
     */
    private Integer opStatus;

    /**
     * 后台审核状态供页面显示
     */
    private Integer opStatusText;

    public Integer getOpStatusText() {
        return opStatusText;
    }

    public void setOpStatusText(Integer opStatusText) {
        this.opStatusText = opStatusText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCancelOrderNo() {
        return cancelOrderNo;
    }

    public void setCancelOrderNo(String cancelOrderNo) {
        this.cancelOrderNo = cancelOrderNo;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public List getDetails() {
        return details;
    }

    public void setDetails(List details) {
        this.details = details;
    }
}
