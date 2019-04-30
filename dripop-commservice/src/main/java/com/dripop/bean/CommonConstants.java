package com.dripop.bean;

import com.dripop.core.util.SpringContextUtil;

/**
 * Created by liyou on 2017/10/18.
 */
public class CommonConstants {

    public final static String SERVICE_PHONE_NUMBER = "SERVICE-PHONE-NUMBER";

    public static final String ROOT_URL = SpringContextUtil.getPropertiesValue("root_url");

    public static final String REPLY_REFUND_REASON = "RETURN-REFUND-REASON";

    public static final String REPLY_GOODS_REASON = "REPLY-GOODS-REASON";

    public static final String ALIPAY_VIP_CARD = "ALIPAY_VIP_CARD";

    public static final String ALIPAY_PLAT_FORM = "1";

    public static final String WEICHAT_PLAT_FORM = "2";

    public static final String CUSTOMER_DEFAULT_HEAD_IMG = "https://static.cdn.dripop.com/default/792497736938402845.png";
}
