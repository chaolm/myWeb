package com.dripop.util;

import com.dripop.core.util.StringUtil;

import java.io.Serializable;

/**
 * Created by liyou on 2018/2/1.
 */
public class LinkUtil implements Serializable {

    private static final String APP_LINK_ROOT = "dripop://";

    /**
     * 获取App link url
     *
     * @param linkType
     * @param refVal
     * @return
     */
    public static String getAppLinkUrl(Integer linkType, String refVal) {
        if (linkType == null || (linkType == 3 && StringUtil.isBlank(refVal)) ||  linkType == 1) {//无链接
            return null;
        } else if (linkType == 2) {//商品详情链接
            return APP_LINK_ROOT + "goods/open/goodsdetail?onlineId=" + refVal;
        } else if (linkType == 3) {//聚合页链接
            return APP_LINK_ROOT + "web?url=" + refVal;
        } else if (linkType == 4) {//订单详情链接
            return APP_LINK_ROOT + "order/detail?orderId=" + refVal;
        } else if (linkType == 5) {//售后详情链接
            return APP_LINK_ROOT + "ordercs/getOrderCancelDetailForRefund?cancelOrderId=" + refVal;
        } else if (linkType == 6) {//卡券中心链接
            return APP_LINK_ROOT + "open/cardCenter";
        } else if (linkType == 7) {//我的卡券链接
            return APP_LINK_ROOT + "customer/cardList?status=" + 1;
        } else {
            return null;
        }
    }
}
