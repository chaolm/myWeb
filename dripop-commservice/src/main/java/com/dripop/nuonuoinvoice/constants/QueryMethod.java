package com.dripop.nuonuoinvoice.constants;

import com.dripop.nuonuoinvoice.bean.PrivateData;
import com.dripop.nuonuoinvoice.bean.PublicData;
import com.dripop.nuonuoinvoice.bean.RequestMode;
import com.dripop.nuonuoinvoice.util.SecurityUtil;

import java.util.HashMap;
import java.util.Map;

public class QueryMethod {

    //定义请求头数据
    public static Map<String,String> getHeaders (){
        Map<String,String> headers = new HashMap<String, String>();
        headers.put("appKey", InvoiceConstants.app_key); //用户申请的appkey
        headers.put("accessToken", InvoiceConstants.app_accessToken); //用户Oauth登录后得到的令牌access token
        headers.put("compress", InvoiceConstants.app_compressType);//压缩方式：提供GZIP 置空“”不压缩
        headers.put("signMethod", InvoiceConstants.app_signType); //加密方式：提供AES ，不可为空
        headers.put("dataType", InvoiceConstants.app_dataType); //数据请求格式： JSON/XML
        headers.put("appRate", InvoiceConstants.app_rate); //app并发请求数  ，平台默认10如需升级请联系开放平台
        headers.put("userTax", InvoiceConstants.user_Tax);//ISV类型用户必填
        return headers;
    }

    //定义开发票请求体中的公共数据
    public static PublicData getPublicDataForRequestBilling(){
        PublicData pdData = new PublicData();
        pdData.setVersion(InvoiceConstants.app_apiVersion); //API版本
        pdData.setTimestamp(String.valueOf(System.currentTimeMillis()));
        pdData.setMethod(InvoiceConstants.request_billing_app_api);//API名称
        return pdData;
    }

    //定义查询发票请求体中的公共数据
    public static PublicData getPublicDataForQuery(){
        PublicData pdData = new PublicData();
        pdData.setVersion(InvoiceConstants.app_apiVersion); //API版本
        pdData.setTimestamp(String.valueOf(System.currentTimeMillis()));
        pdData.setMethod(InvoiceConstants.query_order_app_api);//API名称
        return pdData;
    }

    /**
     * 组装完整请求数据结构
     * @param pdata
     * @param pvData
     * @return
     */
    public static RequestMode getRequestMode(PublicData pdata, PrivateData<Object> pvData){
        RequestMode requestMode = new RequestMode();
        requestMode.setPrivate(pvData);
        requestMode.setPublic(pdata);
        return requestMode;
    }

}
