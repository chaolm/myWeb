package com.dripop.order.dto;

import com.dripop.sys.dto.CustomerOrderDetailDto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class OrderSingleDto implements Serializable {


    private OrderSingleBaseDto orderSingleBaseDto;

    private ReceiptDto receiptDto;

    private ReceiptDto serveMessage;


    private List<LogisticsDto> logisticsDtos;

    private  List<ServerPageDto> serverPageDtos;

    public List<ServerPageDto> getServerPageDtos() {
        return serverPageDtos;
    }

    public void setServerPageDtos(List<ServerPageDto> serverPageDtos) {
        this.serverPageDtos = serverPageDtos;
    }

    private List orderDetails;

    private  List<OrderOperListDto> operListDtos;

    public List<OrderOperListDto> getOperListDtos() {
        return operListDtos;
    }

    public void setOperListDtos(List<OrderOperListDto> operListDtos) {
        this.operListDtos = operListDtos;
    }

    /*   private OrderOperDto orderOperDto;*/



    public OrderSingleBaseDto getOrderSingleBaseDto() {
        return orderSingleBaseDto;
    }

    public void setOrderSingleBaseDto(OrderSingleBaseDto orderSingleBaseDto) {
        this.orderSingleBaseDto = orderSingleBaseDto;
    }

    public ReceiptDto getReceiptDto() {
        return receiptDto;
    }

    public void setReceiptDto(ReceiptDto receiptDto) {
        this.receiptDto = receiptDto;
    }

    public List<LogisticsDto> getLogisticsDtos() {
        return logisticsDtos;
    }

    public void setLogisticsDtos(List<LogisticsDto> logisticsDtos) {
        this.logisticsDtos = logisticsDtos;
    }

  /*  public OrderOperDto getOrderOperDto() {
        return orderOperDto;
    }

    public void setOrderOperDto(OrderOperDto orderOperDto) {
        this.orderOperDto = orderOperDto;
    }*/

    public List getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List orderDetails) {
        this.orderDetails = orderDetails;
    }


    public ReceiptDto getServeMessage() {
        return serveMessage;
    }

    public void setServeMessage(ReceiptDto serveMessage) {
        this.serveMessage = serveMessage;
    }
}
