/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.dripop.alipay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.dripop.core.util.SpringContextUtil;


/**
 * API调用客户端工厂
 */
public class AlipayAPIClientFactory {


    private static final String RSA256 = "RSA2";

    private static final String RSA = "RSA";

    /**
     * 获得API调用客户端
     * @return
     */
    public static AlipayClient getAlipayClient(){
        AlipayConfig alipayConfig = SpringContextUtil.getContext().getBean(AlipayConfig.class);
        return new DefaultAlipayClient(alipayConfig.getAlipayGateway(), alipayConfig.getAppId(),
                    alipayConfig.getPrivateKey(), "json", alipayConfig.getCharset(), alipayConfig.getAlipayPublicKey(), RSA);
    }

    /**
     * 获得API调用客户端
     * @return
     */
    public static AlipayClient getAlipayClient(String appId, String privateKey, String alipayPublicKey){
        AlipayConfig alipayConfig = SpringContextUtil.getContext().getBean(AlipayConfig.class);
        return new DefaultAlipayClient(alipayConfig.getAlipayGateway(), appId,
                    privateKey, "json", alipayConfig.getCharset(), alipayPublicKey, RSA);
    }
}