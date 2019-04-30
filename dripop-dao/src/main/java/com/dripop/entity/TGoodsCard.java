package com.dripop.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by liyou on 2018/2/2.
 */
@Entity
@Table(name = "t_goods_card")
public class TGoodsCard implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "card_id")
    private Long cardId;

    @Column(name = "online_id")
    private Long onlineId;

    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Long onlineId) {
        this.onlineId = onlineId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
