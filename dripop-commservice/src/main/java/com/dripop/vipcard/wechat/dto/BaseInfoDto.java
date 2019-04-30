package com.dripop.vipcard.wechat.dto;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class  BaseInfoDto implements Serializable {

    //卡券的商户logo
    private String logo_url;

    //Code展示类型， "CODE_TYPE_TEXT" 文本 "CODE_TYPE_BARCODE" 一维码 "CODE_TYPE_QRCODE" 二维码 "CODE_TYPE_ONLY_QRCODE" 仅显示二维码 "CODE_TYPE_ONLY_BARCODE" 仅显示一维码 "CODE_TYPE_NONE" 不显示任何码型
    private String code_type="CODE_TYPE_ONLY_QRCODE";

    //商户名字,字数上限为12个汉字。
    private String brand_name;


    //卡券名，字数上限为9个汉字 (建议涵盖卡券属性、服务及金额)
    private String title;

    //券颜色。按色彩规范标注填写Color010-Color100
    private String color="Color010";

    //卡券使用提醒，字数上限为16个汉字。
    private String notice;

    //卡券使用说明，字数上限为1024个汉字。
    private String description;

    SkuDto skuDto=new SkuDto();
    //商品信息
    private JSON sku=(JSON) JSON.toJSON(skuDto);

    //使用时间的类型 支持固定时长有效类型 固定日期有效类型 永久有效类型( DATE_TYPE_PERMANENT)

    //客服电话
    private String service_phone;

    DataInfoDto dateInfo=new DataInfoDto();
    //使用日期
  private JSON date_info=(JSON) JSON.toJSON(dateInfo);

    public JSON getDate_info() {
        return date_info;
    }

    public void setDate_info(JSON date_info) {
        this.date_info = date_info;
    }

    PayInfoDto payInfoDto =new PayInfoDto();
    private JSON pay_info= (JSON)JSON.toJSON(payInfoDto);

    public JSON getPay_info() {
        return pay_info;
    }

    public void setPay_info(JSON pay_info) {
        this.pay_info = pay_info;
    }

    public String getService_phone() {
        return service_phone;
    }

    public void setService_phone(String service_phone) {
        this.service_phone = service_phone;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getCode_type() {
        return code_type;
    }

    public void setCode_type(String code_type) {
        this.code_type = code_type;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JSON getSku() {
        return sku;
    }

    public void setSku(JSON sku) {
        this.sku = sku;
    }

}
