package com.dripop.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by liyou on 2018/2/5.
 */
@Entity
@Table(name = "t_gift")
public class TGift implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "promotion_id")
    private Long promotionId;

    @Column(name = "ref_val")
    private String refVal;

    private Integer type;

    private Integer stock;

    private Integer num;

    @Column(name = "total_num")
    public Integer getTotalNum() {
        return totalNum;
    }
    private Integer totalNum;

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public String getRefVal() {
        return refVal;
    }

    public void setRefVal(String refVal) {
        this.refVal = refVal;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


}
