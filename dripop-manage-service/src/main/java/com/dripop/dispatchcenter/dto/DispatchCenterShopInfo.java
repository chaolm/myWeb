package com.dripop.dispatchcenter.dto;

import java.io.Serializable;

public class DispatchCenterShopInfo implements Serializable {

    //套餐名称
    private String name;
    //图片地址
    private String smallPic;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSmallPic() {
        return smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }
}
