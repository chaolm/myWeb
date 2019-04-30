package com.dripop.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_cancel_order_detail")
public class TCancelOrderDetail implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "cancel_order_id")
    private Long cancelOrderId;

    @Column(name = "order_detail_id")
    private Long orderDetailId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCancelOrderId() {
        return cancelOrderId;
    }

    public void setCancelOrderId(Long cancelOrderId) {
        this.cancelOrderId = cancelOrderId;
    }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }
}
