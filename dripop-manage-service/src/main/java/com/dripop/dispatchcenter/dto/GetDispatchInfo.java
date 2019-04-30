package com.dripop.dispatchcenter.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dripop.util.PriceUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class GetDispatchInfo implements Serializable {

    //下单时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //真实支付
    private Integer realPay;
    private String realPayY;
    //支付方式
    private Integer payModel;
    //用户id
    private Long custId;
    //用户账号
    private String phoneNo;
    //组织名称
    private String name;
    //组织id
    private Long orgId;
    //订单编号
    private String orderNo;
    //订单id
    private Long id;
    //商品名称
    private String goodsNames;
    //商品图片路径
    private String imgUrls;
    //发货时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;
    //状态
    private Integer status;
    //优惠套餐id
    private Long packageId;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getRealPayY() {
        return PriceUtil.getSimplePriceText(realPay);
    }

    public void setRealPayY(String realPayY) {
        this.realPayY = realPayY;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public String getGoodsNames() {
        return goodsNames;
    }

    public void setGoodsNames(String goodsNames) {
        this.goodsNames = goodsNames;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getRealPay() {
        return realPay;
    }

    public void setRealPay(Integer realPay) {
        this.realPay = realPay;
    }

    public Integer getPayModel() {
        return payModel;
    }

    public void setPayModel(Integer payModel) {
        this.payModel = payModel;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
