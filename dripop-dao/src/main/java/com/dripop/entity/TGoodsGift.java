package com.dripop.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by liyou on 2018/3/10.
 */
@Entity
@Table(name = "t_goods_gift")
public class TGoodsGift implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "promotion_id")
    private Long promotionId;

    @Column(name = "online_id")
    private Long onlineId;

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

    public Long getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Long onlineId) {
        this.onlineId = onlineId;
    }
}
