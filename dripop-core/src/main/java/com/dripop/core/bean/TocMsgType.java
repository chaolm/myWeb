package com.dripop.core.bean;

import com.dripop.core.util.MsgTypeInterface;

/**
 * Created by liyou on 2017/9/28.
 */
public enum TocMsgType implements MsgTypeInterface {

    /**
     * 客户注册验证码
     */
    REGISTER_CODE("204583", "【晶杰之家】亲爱的用户，您此次的验证码为：{$var1}，请在5分钟内按页面提示提交。如非本人操作，请忽略本短信"),
    /**
     * 客户注册成功
     */
    REGISTER_SUCC("204584", "【晶杰之家】亲爱的用户，恭喜您选择晶杰之家，您的账号为{$var1}，祝您购物愉快！"),
    /**
     * 客户快捷登录动态密码
     */
    QUICK_LOGIN_CODE("204590", "【晶杰之家】亲爱的用户，您此次的动态密码为：{$var1}，请在5分钟内按页面提示提交。如非本人操作，请忽略本短信"),
    /**
     * 绑定手机验证码
     */
    BIND_PHONE_CODE("204591", "【晶杰之家】亲爱的用户，您此次的验证码为：{$var1}，请在5分钟内按页面提示提交。如非本人操作，请忽略本短信"),
    /**
     * 忘记密码验证码
     */
    FORGET_PWD_CODE("204592", "【晶杰之家】亲爱的用户，您此次的验证码为：{$var1}，请在5分钟内按页面提示提交。如非本人操作，请忽略本短信"),
    /**
     * 到货通知
     */
    GOODS_NOTICE("206857", "【晶杰之家】亲爱的用户，您关注的“{$var1}”已经到货，快去抢购{$var2}"),
    /*门店账户开通成功*/
    STORE_REGISTER_SUCC("206858", "【晶杰之家】恭喜您已成为晶杰之家新会员，点击http://jj100.cn/RHm2jwo下载晶杰之家APP，享会员特权");

    TocMsgType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    private String code;

    private String value;

    private String CODE_HEAD = "smscode";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCodeStr() {
        return CODE_HEAD + getCode();
    }
}
