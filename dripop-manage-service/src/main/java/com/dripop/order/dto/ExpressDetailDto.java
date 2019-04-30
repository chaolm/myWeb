package com.dripop.order.dto;

import java.io.Serializable;

/**
 * 类名及用途
 *
 * @author dq
 * @date 2018/4/16 9:23
 */

public class ExpressDetailDto implements Serializable{
    private String time;

    private String context;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
