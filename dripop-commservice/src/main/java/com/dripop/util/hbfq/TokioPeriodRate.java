package com.dripop.util.hbfq;

import java.io.Serializable;

/**
 * Created by liyou on 2018/3/21.
 */
public class TokioPeriodRate implements Serializable {

    private Integer period;

    private String rate;

    private String sellerRate;

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSellerRate() {
        return sellerRate;
    }

    public void setSellerRate(String sellerRate) {
        this.sellerRate = sellerRate;
    }
}
