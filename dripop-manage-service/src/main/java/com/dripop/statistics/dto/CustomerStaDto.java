package com.dripop.statistics.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 用户统计
 *
 * @author dq
 * @date 2018/3/15 16:07
 */

public class CustomerStaDto implements Serializable {

    /*省份名称*/
    private String province;
    /*用户数量*/
    private Integer sum;
    /*用户占比*/
    private Double portion;


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Double getPortion() {
        return portion;
    }

    public void setPortion(Double portion) {
        this.portion = portion;
    }
}
