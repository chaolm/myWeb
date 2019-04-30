package com.dripop.alipay.dto;

import java.io.Serializable;

/**
 * Created by liyou on 2017/10/24.
 */
public class AlipayConfigDto implements Serializable {

    private String appId;

    private String partner;

    private String myPrivateKey;

    private String alipayPublicKey;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getMyPrivateKey() {
        return myPrivateKey;
    }

    public void setMyPrivateKey(String myPrivateKey) {
        this.myPrivateKey = myPrivateKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }
}
