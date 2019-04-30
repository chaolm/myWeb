package com.dripop.order.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 发货操作信息 回显
 *
 * @author dq
 * @date 2018/3/24 17:54
 */

public class DeliveryOperDto implements Serializable {
    /*订单ID*/
    private Long orderId;
    /*售后ID*/
    private Long cancelId;
    /*2 送货上门 3 上门自提*/
    private Integer shippingModel;
    /*收货人*/
    private String consignee;
    /*收货人手机号*/
    private String consigneePhone;
    /*收货人地址*/
    private String consigneeAdress;
    /*纳税人识别号*/
    private String invoiceNumber;
    /*发票内容*/
    private String invoiceContent;
    /*发票类型 1：普通类型 str*/
    private String invoiceTypeStr;
    /*发票类型 1：普通类型*/
    private Integer invoiceType;
    /*是否需要发票 1 需要 2 不需要*/
    private Integer needInvoice;
    /*发票抬头*/
    private String invoiceName;
    /*客户姓名*/
    private String name;
    /*客户联系方式*/
    private String phoneNo;
    /*订单详情商品信息*/
    private List<DeliveryGoodsDto> deliveryGoodsList;

    public Integer getShippingModel() {
        return shippingModel;
    }

    public void setShippingModel(Integer shippingModel) {
        this.shippingModel = shippingModel;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public String getConsigneeAdress() {
        return consigneeAdress;
    }

    public void setConsigneeAdress(String consigneeAdress) {
        this.consigneeAdress = consigneeAdress;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public String getInvoiceTypeStr() {
        if (invoiceType == null) {
            return "";
        }
        return invoiceType == 1 ? "普通发票" : "";
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Integer getNeedInvoice() {
        return needInvoice;
    }

    public void setNeedInvoice(Integer needInvoice) {
        this.needInvoice = needInvoice;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    public List<DeliveryGoodsDto> getDeliveryGoodsList() {
        return deliveryGoodsList;
    }

    public void setDeliveryGoodsList(List<DeliveryGoodsDto> deliveryGoodsList) {

        this.deliveryGoodsList = deliveryGoodsList;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCancelId() {
        return cancelId;
    }

    public void setCancelId(Long cancelId) {
        this.cancelId = cancelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}