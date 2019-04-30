package com.dripop.statistics.dto;

import java.io.Serializable;

/**
 * 商品用户访问平台来源统计
 *
 * @author dq
 * @date 2018/3/17 11:43
 */

public class ChannelAnalyslsDto implements Serializable {
    /*ios 访问数*/
    private Integer ios;
    /*android 访问数*/
    private Integer android;
    /*pc 访问数*/
    private Integer pc;
    /*h5 访问数*/
    private Integer h5;

    public Integer getIos() {
        return ios;
    }

    public void setIos(Integer ios) {
        this.ios = ios;
    }

    public Integer getAndroid() {
        return android;
    }

    public void setAndroid(Integer android) {
        this.android = android;
    }

    public Integer getPc() {
        return pc;
    }

    public void setPc(Integer pc) {
        this.pc = pc;
    }

    public Integer getH5() {
        return h5;
    }

    public void setH5(Integer h5) {
        this.h5 = h5;
    }
}
