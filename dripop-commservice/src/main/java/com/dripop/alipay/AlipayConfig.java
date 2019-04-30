package com.dripop.alipay;

import com.dripop.core.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by junhu on 2017/10/11.
 */
@Component
@PropertySource({
        "classpath:application-common.properties"
})
public class AlipayConfig implements Serializable {

    @Value("${app_id}")
    private String appId;

    @Value("${alipay_life_app_id}")
    private String lifeAppId;

    @Value("${seller_id}")
    private String sellerId;

    @Value("${partner}")
    private String partner;

    @Value("${private_key}")
    private String privateKey;

    @Value("${alipay_public_key}")
    private String alipayPublicKey;

    @Value("${alipay_life_public_key}")
    private String alipayLifePublicKey;

    @Value("${alipay_wap_public_key}")
    private String alipayWapPublicKey;

    private String alipayGateway = "https://openapi.alipay.com/gateway.do";

    private String charset = "UTF-8";

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getAlipayWapPublicKey() {
        return alipayWapPublicKey;
    }

    public void setAlipayWapPublicKey(String alipayWapPublicKey) {
        this.alipayWapPublicKey = alipayWapPublicKey;
    }

    public String getAlipayGateway() {
        return alipayGateway;
    }

    public void setAlipayGateway(String alipayGateway) {
        this.alipayGateway = alipayGateway;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getLifeAppId() {
        return lifeAppId;
    }

    public void setLifeAppId(String lifeAppId) {
        this.lifeAppId = lifeAppId;
    }

    public String getAlipayLifePublicKey() {
        return alipayLifePublicKey;
    }

    public void setAlipayLifePublicKey(String alipayLifePublicKey) {
        this.alipayLifePublicKey = alipayLifePublicKey;
    }
}
