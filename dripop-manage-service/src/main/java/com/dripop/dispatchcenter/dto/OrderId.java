package com.dripop.dispatchcenter.dto;

import java.io.Serializable;
import java.util.List;

public class OrderId  implements Serializable{
    private List<Long> longs;

    public List<Long> getLongs() {
        return longs;
    }

    public void setLongs(List<Long> longs) {
        this.longs = longs;
    }
}
