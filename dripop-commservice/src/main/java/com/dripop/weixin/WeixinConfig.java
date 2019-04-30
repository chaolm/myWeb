package com.dripop.weixin;

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
public class WeixinConfig implements Serializable {

    @Value("${wechat_appid}")
    private String appId;

    @Value("${wechat_appsecret}")
    private String appSecret;

    @Value("${wechat_partner}")
    private String partner;

    @Value("${wechat_partner_key}")
    private String partnerKey;

    @Value("${public_wechat_appid}")
    private String publicAppId;

    @Value("${public_wechat_appsecret}")
    private String publicAppSecret;

    @Value("${public_wechat_partner}")
    private String publicPartner;

    @Value("${public_wechat_partner_key}")
    private String publicPartnerKey;

    @Value("${wechat_pcid}")
    private String appIdPc;

    @Value("${wechat_pcsecret}")
    private String appSecretPc;

    @Value("${redirect_uri}")
    private String redirectUriPc;

    public String getAppIdPc() {
        return appIdPc;
    }

    public void setAppIdPc(String appIdPc) {
        this.appIdPc = appIdPc;
    }

    public String getAppSecretPc() {
        return appSecretPc;
    }

    public void setAppSecretPc(String appSecretPc) {
        this.appSecretPc = appSecretPc;
    }

    public String getRedirectUriPc() {
        return redirectUriPc;
    }

    public void setRedirectUriPc(String redirectUriPc) {
        this.redirectUriPc = redirectUriPc;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getPartnerKey() {
        return partnerKey;
    }

    public void setPartnerKey(String partnerKey) {
        this.partnerKey = partnerKey;
    }

    public String getPublicAppId() {
        return publicAppId;
    }

    public void setPublicAppId(String publicAppId) {
        this.publicAppId = publicAppId;
    }

    public String getPublicAppSecret() {
        return publicAppSecret;
    }

    public void setPublicAppSecret(String publicAppSecret) {
        this.publicAppSecret = publicAppSecret;
    }

    public String getPublicPartner() {
        return publicPartner;
    }

    public void setPublicPartner(String publicPartner) {
        this.publicPartner = publicPartner;
    }

    public String getPublicPartnerKey() {
        return publicPartnerKey;
    }

    public void setPublicPartnerKey(String publicPartnerKey) {
        this.publicPartnerKey = publicPartnerKey;
    }
}
