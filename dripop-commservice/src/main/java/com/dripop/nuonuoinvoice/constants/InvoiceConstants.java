package com.dripop.nuonuoinvoice.constants;

public class InvoiceConstants {

    /**
     * 开发票销方电话
     */
    public final static String company_phone = "0571-87068008";

    /**
     * 开发票销方地址
     */
    public final static String company_address = "杭州市下城区长天弄46号";

    /**
     * 极速开票密钥
     */
    public final static String speed_billing = "F59E1B2970D64B8F926E9932E0A1A1DB";

    /**
     * APP 调用接口需填写以下系统参数
     */
    public final static String app_secret = "444BC33069514816";  //填写本APP申请的appSecret

    public static String app_key = "wTG2GdCI";  //填写本APP申请的 appKey

    public static String query_order_app_api = "nuonuo.electronInvoice.querySerialNum";  //通过订单号查询发票请求流水号、发票状态及发票明细

    public static String query_serialNum_app_api = "nuonuo.electronInvoice.CheckEInvoice"; //通过开票流水号查询电子发票明细，支持批量查询

    public static String request_billing_app_api = "nuonuo.electronInvoice.requestBilling"; //具备电子发票资质的企业用户填写电子发票销方、购方、明细等信息并发起开票请求

    public static String app_apiVersion = "1.0";  //填写本APP调用接口版本，如：1.0.0

    public static String app_accessToken = "8f37ebe86f59ec82a5d5fd02hyuzjzms";  //填写本APP申请的令牌

    public static String app_dataType = "JSON";  //填写本APP传输数据格式，如：JSON,XML

    public static String app_compressType = "GZIP";  //填写本APP传输数据压缩格式，如：GZIP

    public static String app_signType = "AES";  //填写本APP传输数据加密格式，如：AES

    public static String test_user_Tax = "339901999999142";  //业务发生方税号（ISV类型用户必填）-测试环境

    public static String user_Tax = "91330100768217127K";  //业务发生方税号（ISV类型用户必填）-生产环境

    /**
     * APP 请求并发数（平台默认）
     */
    public static String app_rate = "10"; //填写APP并发请求数

    /**
     * 开放平台访问地址-沙箱环境
     */
    public static String test_url = "https://sandbox.jss.com.cn/openPlatform/services";

    /**
     * 开放平台访问地址-生产环境
     */
    public static String production_url = "https://sdk.jss.com.cn/openPlatform/services";

}
