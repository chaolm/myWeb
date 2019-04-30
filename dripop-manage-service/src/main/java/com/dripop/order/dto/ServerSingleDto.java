package com.dripop.order.dto;

import java.io.Serializable;
import java.util.List;

public class ServerSingleDto implements Serializable {

    /*换货 退货原因*/
    private ServerSingleBaseDto serverSingleBaseDto;

    /*寄件物流信息*/
    private LogisticsDto sendLogisticsDto;

    /*收件物流信息*/
    private LogisticsDto putLogisticsDto;

    /*收货信息*/
    private ReceiptDto receiptDto;

    /*服务信息*/
    private ReceiptDto serveMessage;

    /*订单商品信息*/
    private List orderDetails;


    private List<ExpressMsgDto> expressMsgDtos;

    public List<ExpressMsgDto> getExpressMsgDtos() {
        return expressMsgDtos;
    }

    public void setExpressMsgDtos(List<ExpressMsgDto> expressMsgDtos) {
        this.expressMsgDtos = expressMsgDtos;
    }

    private DeliveryOperDto deliveryOperDto;

    public DeliveryOperDto getDeliveryOperDto() {
        return deliveryOperDto;
    }

    public void setDeliveryOperDto(DeliveryOperDto deliveryOperDto) {
        this.deliveryOperDto = deliveryOperDto;
    }

    public ServerSingleBaseDto getServerSingleBaseDto() {
        return serverSingleBaseDto;
    }

    public void setServerSingleBaseDto(ServerSingleBaseDto serverSingleBaseDto) {
        this.serverSingleBaseDto = serverSingleBaseDto;
    }

    public LogisticsDto getSendLogisticsDto() {
        return sendLogisticsDto;
    }

    public void setSendLogisticsDto(LogisticsDto sendLogisticsDto) {
        this.sendLogisticsDto = sendLogisticsDto;
    }

    public LogisticsDto getPutLogisticsDto() {
        return putLogisticsDto;
    }

    public void setPutLogisticsDto(LogisticsDto putLogisticsDto) {
        this.putLogisticsDto = putLogisticsDto;
    }

    public ReceiptDto getReceiptDto() {
        return receiptDto;
    }

    public void setReceiptDto(ReceiptDto receiptDto) {
        this.receiptDto = receiptDto;
    }

    public ReceiptDto getServeMessage() {
        return serveMessage;
    }

    public void setServeMessage(ReceiptDto serveMessage) {
        this.serveMessage = serveMessage;
    }

    public List getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List orderDetails) {
        this.orderDetails = orderDetails;
    }
}
