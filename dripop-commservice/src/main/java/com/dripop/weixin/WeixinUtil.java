package com.dripop.weixin;

import com.alibaba.fastjson.JSONObject;
import com.bean.OrderType;
import com.bean.PayModel;
import com.dripop.constant.ApiAddressConstant;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.*;
import com.dripop.entity.TOrder;
import com.dripop.qq.QqConfig;
import com.dripop.wechat.bean.TicketVo;
import com.dripop.wechat.bean.TokenVo;
import com.dripop.wechat.bean.WeChatConfigVo;
import com.dripop.wechat.bean.WeixinInfo;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


public class WeixinUtil {

    private static final Logger logger = LoggerFactory.getLogger(WeixinUtil.class);

    public static final String APP = "APP";
    public static final String MWEB = "MWEB";
    public static final String JSAPI = "JSAPI";
    public static final String NATIVE = "NATIVE";

    //这个地址是腾讯提供的
    private static String tokenServiceUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
    private static String tokenServiceUrlWeixin = "https://api.weixin.qq.com/sns/oauth2/access_token";
    private static String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo";
    //这个地址是腾讯提供的
    private static String ticketServiceUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
    private static String tokenServiceUrlQQ = "https://graph.qq.com/oauth2.0/token";

    public final static String WECHAT_ACCESS_TOKEN = "CIRCLE_WECHAT_ACCESS_TOKEN";
    public final static String WECHAT_ACCESS_TICKET = "CIRCLE_WECHAT_ACCESS_TICKET";

    /**
     * 微信分享需要初始化一些数据，详情见前段js文件
     * 把这个对象的四个字段填充到js页面
     *
     * @return
     */
    public static WeChatConfigVo getWeChatConfig(String appId, String appSecret, String shareUrl) {
        //(微信)获取access_token
        TokenVo tokenVo = getTokenVo(appId, appSecret);

        //(微信)获取ticket【根据tokenVo获取ticketVo】
        TicketVo ticketVo = getTicketVo(tokenVo);

        //(微信)获取签名signature
        Map<String, String> valueMap = getTargetMap(ticketVo, shareUrl);

        WeChatConfigVo configVo = new WeChatConfigVo();
        configVo.setWeChatAppId(appId);
        configVo.setWeChatNonceStr(valueMap.get("noncestr"));
        configVo.setWeChatSignature(valueMap.get("signature"));
        configVo.setWeChatTimestamp(valueMap.get("timestamp"));
        return configVo;
    }

    /**
     * (微信)获取access_token
     *
     * @return
     * @date 2016年01月9日下午1:31:56
     */
    public static TokenVo getTokenVo(String appId, String appSecret) {
        TokenVo tokenVo = RedisUtil.INSTANCE.get(WECHAT_ACCESS_TOKEN, TokenVo.class);
        if (tokenVo == null || StringUtil.isBlank(tokenVo.getAccess_token())) {
            tokenVo = JsonUtil.fromJson(HttpUtil.post(tokenServiceUrl+"&appid="+appId+"&secret="+appSecret, ""), TokenVo.class);
            if (tokenVo != null && StringUtil.isNotBlank(tokenVo.getAccess_token())) {
                RedisUtil.INSTANCE.setnx(WECHAT_ACCESS_TOKEN, JsonUtil.toJson(tokenVo), 6000);
            }
        }
        return tokenVo;
    }

    /**
<<<<<<< HEAD
<<<<<<< Updated upstream
=======
=======
>>>>>>> develop
     * 获取acess_token
     * @return
     */
    public static String getTokenForCard() {
        WeixinConfig weixinConfig = SpringContextUtil.getContext().getBean(WeixinConfig.class);
        TokenVo tokenVo=null;
        try {
            tokenVo = getTokenVo(weixinConfig.getPublicAppId(), weixinConfig.getPublicAppSecret());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return tokenVo.getAccess_token();
    }

    /**
     * 微信扫码登陆获取属性
     * @return
     */
    public static Map getWeixinParam() {
        Map jsonUser = new HashMap();
        jsonUser.put("response_type","code");
        jsonUser.put("appid",SpringContextUtil.getContext().getBean(WeixinConfig.class).getAppIdPc());
        jsonUser.put("scope","snsapi_login");
        jsonUser.put("redirect_uri",SpringContextUtil.getContext().getBean(WeixinConfig.class).getRedirectUriPc());
        return jsonUser;
    }
    /**
     * Pc微信扫码登陆
     * @param code
     * @return
     */
    public static WeixinInfo loginByWeChat(String code) {
        TokenVo tokenVo = WeixinUtil.getTokenForWeChatLogin(code);
        String s= HttpUtil.get(getUserInfoUrl+"?&access_token="+tokenVo.getAccess_token()+"&openid="+tokenVo.getOpenid());
        WeixinInfo weixinInfo = JsonUtil.fromJson(HttpUtil.get(getUserInfoUrl+"?&access_token="+tokenVo.getAccess_token()+"&openid="+tokenVo.getOpenid()), WeixinInfo.class);
        return weixinInfo;
    }

    /**
     * Pc微信扫码登陆获取acess_token
     * @return
     */
    public static TokenVo getTokenForWeChatLogin(String code) {
        TokenVo tokenVo=null;
        try {
            WeixinConfig weixinConfig = SpringContextUtil.getContext().getBean(WeixinConfig.class);
            String url = HttpUtil.get(tokenServiceUrlWeixin+"?&appid="+weixinConfig.getAppIdPc()+"&secret="+weixinConfig.getAppSecretPc()+"&code="+code+"&grant_type=authorization_code");
            tokenVo = JsonUtil.fromJson(url, TokenVo.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return tokenVo;
    }

    /**
     * 扫码登陆获取acess_token（QQ）
     * @return
     */
    public static TokenVo getTokenForQQLogin(String code, String redirectUri) {
        QqConfig qqConfig = SpringContextUtil.getContext().getBean(QqConfig.class);
        TokenVo tokenVo=null;
        try {
            tokenVo = getLoginQQTokenVo(qqConfig.getAppId(), qqConfig.getAppSecret(),code,redirectUri);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return tokenVo;
    }

    /**
     * 扫码登陆获取acess_token（QQ）
     * @param appId
     * @param appSecret
     * @param code
     * @return
     * @throws Exception
     */
    private static TokenVo getLoginQQTokenVo(String appId, String appSecret,String code,String redirectUri) throws Exception {
        TokenVo tokenVo = RedisUtil.INSTANCE.get(WECHAT_ACCESS_TOKEN, TokenVo.class);
        if (tokenVo == null || StringUtil.isBlank(tokenVo.getAccess_token())) {
            Map<String, String> tokenValueMap = new HashMap<String, String>();
            tokenValueMap.put("client_id", appId);
            tokenValueMap.put("client_secret", appSecret);
            tokenValueMap.put("code", code);
            tokenValueMap.put("grant_type","authorization_code");
            tokenValueMap.put("redirect_uri",redirectUri);
            tokenVo = JsonUtil.fromJson(HttpUtil.get(tokenServiceUrlQQ, JsonUtil.toJson(tokenValueMap)), TokenVo.class);
            if (tokenVo != null && StringUtil.isNotBlank(tokenVo.getAccess_token())) {
                RedisUtil.INSTANCE.setnx(WECHAT_ACCESS_TOKEN, JsonUtil.toJson(tokenVo), 6000);
            }
        }
        return tokenVo;
    }

    /**
>>>>>>> Stashed changes
=======
>>>>>>> develop
     * (微信)获取ticket
     *
     * @param tokenVo
     * @return
     * @date 2016年01月9日下午1:32:32
     */
    public static TicketVo getTicketVo(TokenVo tokenVo) {
        TicketVo ticketVo = RedisUtil.INSTANCE.get(WECHAT_ACCESS_TICKET, TicketVo.class);
        if (ticketVo == null || StringUtil.isBlank(ticketVo.getTicket())) {
            ticketVo = JsonUtil.fromJson(HttpUtil.post(ticketServiceUrl+"?access_token="+tokenVo.getAccess_token()+"&type=jsapi", ""), TicketVo.class);
            if (ticketVo != null && StringUtil.isNotBlank(ticketVo.getTicket())) {
                RedisUtil.INSTANCE.setnx(WECHAT_ACCESS_TICKET, JsonUtil.toJson(ticketVo), 6000);
            }
        }
        System.out.println(JsonUtil.toJson(ticketVo));
        return ticketVo;
    }

    public static String getOpenId(String code) {
        JSONObject jsonObject = getAccessToken(code);
        return jsonObject.getString("openid");
    }

    public static JSONObject getAccessToken(String code) {
        WeixinConfig weixinConfig = SpringContextUtil.getContext().getBean(WeixinConfig.class);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + weixinConfig.getPublicAppId() + "&secret=" + weixinConfig.getPublicAppSecret() + "&code=" + code + "&grant_type=authorization_code";
        String jsonStr = HttpUtil.post(url, "");
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        logger.info("get wechat openid info : {}", JsonUtil.toFullJson(jsonObject));
        if (StringUtil.isNotBlank(jsonObject.getString("errcode"))) {
            throw new ServiceException(jsonObject.getString("errmsg"));
        }
        return jsonObject;
    }

    /**
     * 判断微信用户是否关注公众号 1 已关注 0 未关注
     * @param accessToken
     * @param openId
     * @return
     */
    public static Integer isSubscribe(String accessToken, String openId) {
        String userInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";
        String jsonStr = HttpUtil.post(userInfoUrl, "");
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        logger.info(jsonObject.toJSONString());
        Integer subscribe = jsonObject.getInteger("subscribe");
        return subscribe;
    }

    public static String createPackage(String prepay_id) {
        WeixinConfig weixinConfig = SpringContextUtil.getContext().getBean(WeixinConfig.class);
        SortedMap<String, String> map = new TreeMap<String, String>();
        String timestamp = (new Date().getTime() / 1000) + "";
        String nonceStr = StringUtil.getRadomString(6, 2);
        String packages = "prepay_id=" + prepay_id;
        map.put("appId", weixinConfig.getPublicAppId());
        map.put("timeStamp", timestamp);
        map.put("nonceStr", nonceStr);
        map.put("package", packages);
        map.put("signType", "MD5");

        String sign = createSign(map);
        String payInfo = "\"appId\":\"" + weixinConfig.getPublicAppId() + "\",\"timeStamp\":\"" + timestamp
                + "\",\"nonceStr\":\"" + nonceStr + "\",\"package\":\""
                + packages + "\",\"signType\" : \"MD5" + "\",\"paySign\":\""
                + sign + "\"";
        return payInfo;
    }

    /**
     * 创建微信订单
     *
     * @param order
     * @param trade_type
     * @param openId
     * @return
     */
    public static Map createOrder(TOrder order, String trade_type, String openId) {
        WeixinConfig weixinConfig = SpringContextUtil.getContext().getBean(WeixinConfig.class);
        // 订单号
        String orderId = order.getId().toString();
        // 附加数据 原样返回
        String attach = order.getId().toString();

        // 订单生成的机器 IP
        String spbill_create_ip = SessionUtil.getIpAddr();
        // 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
        String notify_url = ApiAddressConstant.SERVER_API_ROOT + "/callback/weixinNotify";

//	  String trade_type = "NATIVE";
//	  String trade_type = "APP";
        // 商户号
        String mch_id = weixinConfig.getPartner();
        String appid = weixinConfig.getAppId();
        if (trade_type.equals(WeixinUtil.JSAPI)) {
            mch_id = weixinConfig.getPublicPartner();
            appid = weixinConfig.getPublicAppId();
        }
        // 随机字符串
        String nonce_str = StringUtil.getRadomString(6, 2);
        // 商品描述根据情况修改
        String body = order.getRemark();

        // 总金额以分为单位，不带小数点
        String totalFee = order.getRealPay().toString();
        // 商户订单号
        String out_trade_no = order.getOrderNo();

        //预售处理
        if (OrderType.PRESELL.getValue().equals(order.getOrderType())&&order.getDepositPay() != null) {
            if (  order.getDepositPayStatus() == 0) {
                order.setPresellPayStage(1);
                totalFee = order.getDepositPay().toString();
                out_trade_no = order.getDepositNo();
            } else {
                order.setPresellPayStage(2);
                totalFee = String.valueOf(order.getRealPay() - (order.getDepositPay() == null ? 0 : order.getDepositPay()));
            }
        }

        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", appid);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", body);
        packageParams.put("attach", attach);
        packageParams.put("out_trade_no", out_trade_no);

        // 这里写的金额为1 分到时修改
        packageParams.put("total_fee", totalFee);
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", notify_url);
        packageParams.put("trade_type", trade_type);

        String xml = null;
        String sign = createSign(packageParams);
        if (trade_type.equals(WeixinUtil.JSAPI)) {
            packageParams.put("openid", openId);
            sign = createSign(packageParams);
            xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
                    + mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
                    + "</nonce_str>" + "<sign>" + sign + "</sign>"
                    + "<body><![CDATA[" + body + "]]></body>"
                    + "<out_trade_no>" + out_trade_no
                    + "</out_trade_no>" + "<attach>" + attach + "</attach>"
                    + "<total_fee>" + totalFee + "</total_fee>"
                    + "<spbill_create_ip>" + spbill_create_ip
                    + "</spbill_create_ip>" + "<notify_url>" + notify_url
                    + "</notify_url>" + "<openid>" + openId
                    + "</openid>" + "<trade_type>" + trade_type
                    + "</trade_type>" + "</xml>";
        } else if(trade_type.equals(WeixinUtil.NATIVE)){
            xml="<xml>" + "<appid>" +appid + "</appid>" + "<mch_id>"
                    + mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
                    + "</nonce_str>" + "<sign>" + sign + "</sign>"
                    + "<body><![CDATA[" + body + "]]></body>"
                    + "<out_trade_no>" + out_trade_no
                    + "</out_trade_no>" + "<attach>" + attach + "</attach>"
                    + "<total_fee>" + totalFee + "</total_fee>"
                    + "<spbill_create_ip>" + spbill_create_ip
                    + "</spbill_create_ip>" + "<notify_url>" + notify_url
                    + "</notify_url>" + "<trade_type>" + trade_type
                    + "</trade_type>" + "</xml>";
           /* String code_url="";
            String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

            code_url = getCodeUrl(createOrderURL, xml);
            Map map=new HashMap();
            map.put("url",code_url);
            return map;*/
        }
        else {
            xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
                    + mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
                    + "</nonce_str>" + "<sign>" + sign + "</sign>"
                    + "<body><![CDATA[" + body + "]]></body>"
                    + "<out_trade_no>" + out_trade_no
                    + "</out_trade_no>" + "<attach>" + attach + "</attach>"
                    + "<total_fee>" + totalFee + "</total_fee>"
                    + "<spbill_create_ip>" + spbill_create_ip
                    + "</spbill_create_ip>" + "<notify_url>" + notify_url
                    + "</notify_url>" + "<trade_type>" + trade_type
                    + "</trade_type>" + "</xml>";
        }

        String result = HttpUtil.post("https://api.mch.weixin.qq.com/pay/unifiedorder", xml, HttpUtil.XML);
        System.out.println(result + "-------------------------");
        Map map = null;
        try {
            map = doXMLParse(result);
            if (trade_type.equals(WeixinUtil.MWEB)) {
                map.put("mweb_url", map.get("mweb_url") + "&redirect_url=" + ApiAddressConstant.JJZJ_H5_ROOT +
                        "/#/paymentResults?orderId=" + orderId + "&payModel=" + PayModel.WECHAT.getValue());
            }
        } catch (Exception e) {
            new ServiceException(e.getMessage());
        }
        return map;
    }



    /**
  //   *description:进行微信统一下单
  //   *@param url
  //   *@param xmlParam
  //   *@return
  //   * @author ex_yangxiaoyi
  //   * @see
  //   */
//  public static Map<String, Object> wxUnifiedorder(String url,String xmlParam){
//	  DefaultHttpClient client = new DefaultHttpClient();
//	  client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
//	  HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
//	  Map<String, Object> resultMap = null;
//     try {
//		 httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
//		 HttpResponse response = httpclient.execute(httpost);
//		 String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
//		 System.out.println(jsonStr);
//		 resultMap = doXMLParse(jsonStr);
//	} catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	return resultMap;
//  }

    /**
     * 关闭订单
     *
     * @param orderNo
     * @throws Exception
     */
    public static void closeWechatOrder(String orderNo, String appid) {
        WeixinConfig weixinConfig = SpringContextUtil.getContext().getBean(WeixinConfig.class);
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        String mch_id = null;
        if (appid.equals(weixinConfig.getAppId())) {
            packageParams.put("appid", weixinConfig.getAppId());
            packageParams.put("mch_id", weixinConfig.getPartner());
            mch_id = weixinConfig.getPartner();
        } else {
            packageParams.put("appid", weixinConfig.getPublicAppId());
            packageParams.put("mch_id", weixinConfig.getPublicPartner());
            mch_id = weixinConfig.getPublicPartner();
        }
        packageParams.put("out_trade_no", orderNo);
        String nonce_str = StringUtil.getRadomString(6, 2);
        packageParams.put("nonce_str", nonce_str);

        String sign = createSign(packageParams);

        String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
                + mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
                + "</nonce_str>" + "<sign>" + sign + "</sign><out_trade_no>" + orderNo + "</out_trade_no></xml>";
        String result = HttpUtil.post("https://api.mch.weixin.qq.com/pay/closeorder", xml, HttpUtil.XML);
        if (result.contains("FAIL")) {
            System.out.println("微信订单" + orderNo + "关闭订单失败");
            System.out.println(result);
        } else {
            System.out.println("微信订单" + orderNo + "关闭订单成功");
        }
    }

//	/**
//	 * 退款接口
//	 * @param orderNo
//	 * @param outRefundNo
//	 * @param totalFee
//	 * @param refundFee
//	 */
//	public static void refundOrder(String orderNo, String outRefundNo, Integer totalFee, Integer refundFee, String appid) {
//
//		WeixinConfig weixinConfig = SpringContextUtil.getContext().getBean(WeixinConfig.class);
//		SortedMap<String, String> packageParams = new TreeMap<String, String>();
//		String mch_id = null;
//		if(appid.equals(weixinConfig.getAppId())) {
//			packageParams.put("appid", weixinConfig.getAppId());
//			packageParams.put("mch_id", weixinConfig.getPartner());
//			mch_id = weixinConfig.getPartner();
//		}else {
//			packageParams.put("appid", weixinConfig.getPublicAppId());
//			packageParams.put("mch_id", weixinConfig.getPublicPartner());
//			mch_id = weixinConfig.getPublicPartner();
//		}
//		packageParams.put("out_trade_no", orderNo);
//		String nonce_str = StringUtil.getRadomString(6, 2);
//		packageParams.put("nonce_str", nonce_str);
//		packageParams.put("out_refund_no", orderNo + outRefundNo);
//		packageParams.put("total_fee", totalFee.toString());
//		packageParams.put("refund_fee", refundFee.toString());
//		String sign = createSign(packageParams);
//
//		String xml = "<xml>" + "<appid>" + appid + "</appid>" +
//				"<mch_id>" + mch_id + "</mch_id>" +
//				"<nonce_str>" + nonce_str + "</nonce_str>" +
//				"<sign>" + sign + "</sign>" +
//				"<out_trade_no>" + orderNo + "</out_trade_no>" +
//				"<out_refund_no>" + orderNo + outRefundNo + "</out_refund_no>" +
//				"<total_fee>" + totalFee + "</total_fee>" +
//				"<refund_fee>" + refundFee + "</refund_fee>" +
//				"</xml>";
//
//		String result = HttpUtil.post("https://api.mch.weixin.qq.com/secapi/pay/refund", xml, HttpUtil.XML);
//		Map map = doXMLParse(result);
//		if (map.get("return_code").toString().equals("FAIL")) {
//			throw new ServiceException("微信错误:" + map.get("return_msg").toString());
//		}else if(map.get("result_code").toString().equals("FAIL")){
//			throw new ServiceException("微信错误:" + map.get("err_code").toString() + ":" + map.get("err_code_des").toString());
//		}
//	}

    public static void refundOrder(String orderNo, String outRefundNo, Integer totalFee, Integer refundFee, String appid) {

        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");

            WeixinConfig weixinConfig = SpringContextUtil.getContext().getBean(WeixinConfig.class);
            String cert = null;
            String password = null;
            if (appid.equals(weixinConfig.getAppId())) {
                cert = "apiclient_cert1.p12";
                password = "1427701902";
            } else {
                cert = "apiclient_cert.p12";
                password = "1291684401";
            }
//			Resource resource = new ClassPathResource(cert);
//			File file = resource.getFile();
////			InputStream instream = WeixinUtil.class.getResourceAsStream(cert);
//			InputStream instream = new FileInputStream(file);
            InputStream instream = WeixinUtil.class.getClassLoader().getResourceAsStream(cert);
            try {
                keyStore.load(instream, password.toCharArray());
            } finally {
                instream.close();
            }

            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, password.toCharArray())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();

            SortedMap<String, String> packageParams = new TreeMap<String, String>();
            String mch_id = null;
            if (appid.equals(weixinConfig.getAppId())) {
                packageParams.put("appid", weixinConfig.getAppId());
                packageParams.put("mch_id", weixinConfig.getPartner());
                mch_id = weixinConfig.getPartner();
            } else {
                packageParams.put("appid", weixinConfig.getPublicAppId());
                packageParams.put("mch_id", weixinConfig.getPublicPartner());
                mch_id = weixinConfig.getPublicPartner();
            }
            packageParams.put("appid", appid);
            packageParams.put("mch_id", mch_id);
            packageParams.put("out_trade_no", orderNo);
            String nonce_str = StringUtil.getRadomString(6, 2);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("out_refund_no", orderNo + outRefundNo);
            packageParams.put("total_fee", totalFee.toString());
            packageParams.put("refund_fee", refundFee.toString());

            String sign = createSign(packageParams);

            String xml = "<xml>" + "<appid>" + appid + "</appid>" +
                    "<mch_id>" + mch_id + "</mch_id>" +
                    "<nonce_str>" + nonce_str + "</nonce_str>" +
                    "<sign>" + sign + "</sign>" +
                    "<out_trade_no>" + orderNo + "</out_trade_no>" +
                    "<out_refund_no>" + orderNo + outRefundNo + "</out_refund_no>" +
                    "<total_fee>" + totalFee + "</total_fee>" +
                    "<refund_fee>" + refundFee + "</refund_fee>" +
                    "</xml>";

            try {

                HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");
                httpPost.setEntity(new StringEntity(xml, "UTF-8"));

                System.out.println("executing request" + xml);

                CloseableHttpResponse response = httpclient.execute(httpPost);
                try {

                    String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                    Map map = doXMLParse(jsonStr);
                    if (map.get("return_code").toString().equals("FAIL")) {
                        throw new RuntimeException("微信错误:" + map.get("return_msg").toString());
                    } else if (map.get("result_code").toString().equals("FAIL")) {
                        throw new RuntimeException("微信错误:" + map.get("err_code").toString() + ":" + map.get("err_code_des").toString());
                    }
                } finally {
                    response.close();
                }
            } finally {
                httpclient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("退款失败");
        }

    }


    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     *
     * @param strxml
     * @return
     * @throws IOException
     */
    public static Map doXMLParse(String strxml) {
        try {
            if (null == strxml || "".equals(strxml)) {
                return null;
            }

            Map m = new HashMap();
            InputStream in = String2Inputstream(strxml);
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(in);
            Element root = doc.getRootElement();
            List list = root.getChildren();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String k = e.getName();
                String v = "";
                List children = e.getChildren();
                if (children.isEmpty()) {
                    v = e.getTextNormalize();
                } else {
                    v = getChildrenText(children);
                }

                m.put(k, v);
            }

            //关闭流
            in.close();

            return m;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 获取子结点的xml
     *
     * @param children
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    public static InputStream String2Inputstream(String str) throws UnsupportedEncodingException {
        return new ByteArrayInputStream(str.getBytes("utf-8"));
    }

    public final static Map queryOrder(String orderNo, String appid) throws Exception {
        WeixinConfig weixinConfig = SpringContextUtil.getContext().getBean(WeixinConfig.class);
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        String mch_id = null;
        if (appid.equals(weixinConfig.getAppId())) {
            packageParams.put("appid", weixinConfig.getAppId());
            packageParams.put("mch_id", weixinConfig.getPartner());
            mch_id = weixinConfig.getPartner();
        } else {
            packageParams.put("appid", weixinConfig.getPublicAppId());
            packageParams.put("mch_id", weixinConfig.getPublicPartner());
            mch_id = weixinConfig.getPublicPartner();
        }
        packageParams.put("out_trade_no", orderNo);
        String nonce_str = StringUtil.getRadomString(6, 2);
        packageParams.put("nonce_str", nonce_str);
        String sign = createSign(packageParams);

        String xml = "<xml>" + "<appid>" + appid + "</appid>" +
                "<mch_id>" + mch_id + "</mch_id>" +
                "<nonce_str>" + nonce_str + "</nonce_str>" +
                "<sign>" + sign + "</sign>" +
                "<out_trade_no>" + orderNo + "</out_trade_no>" +
                "</xml>";

        String result = HttpUtil.post("https://api.mch.weixin.qq.com/pay/orderquery", xml, HttpUtil.XML);
        Map resultMap = doXMLParse(result);
        return resultMap;
    }

    public final static Map toScanCode(TOrder order, String authCode) {
        WeixinConfig weixinConfig = SpringContextUtil.getContext().getBean(WeixinConfig.class);
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", weixinConfig.getAppId());
        packageParams.put("mch_id", weixinConfig.getPartner());
        String nonce_str = StringUtil.getRadomString(6, 2);
        packageParams.put("nonce_str", nonce_str);
        /* String notify_url = ApiAddressConstant.SERVER_API_ROOT + "/callback/weixinNotify";
        packageParams.put("notify_url", notify_url);*/
        packageParams.put("body", order.getOrderNo());
//		packageParams.put("attach", orderNo);
        packageParams.put("out_trade_no", order.getOrderNo());

        // 这里写的金额为1 分到时修改
        packageParams.put("total_fee", order.getRealPay().toString());
        packageParams.put("spbill_create_ip", SessionUtil.getIpAddr());

        packageParams.put("auth_code", authCode);

        String sign = createSign(packageParams);

        String xml = "<xml>\n" +
                "   <appid>" + weixinConfig.getAppId() + "</appid>\n" +
//				"   <attach>订单额外描述</attach>\n" +
                "   <auth_code>" + authCode + "</auth_code>\n" +
                "   <body>" + order.getOrderNo() + "</body>\n" +
//				"   <device_info>1000</device_info>\n" +
//				"   <goods_tag></goods_tag>\n" +
                "   <mch_id>" + weixinConfig.getPartner() + "</mch_id>\n" +
                "   <nonce_str>" + nonce_str + "</nonce_str>\n" +
                // 	"   <notify_url>"+notify_url+"</notify_url>\n" +
                "   <out_trade_no>" + order.getOrderNo() + "</out_trade_no>\n" +
                "   <spbill_create_ip>" + SessionUtil.getIpAddr() + "</spbill_create_ip>\n" +
//				"   <time_expire></time_expire>\n" +
                "   <total_fee>" + order.getRealPay() + "</total_fee>\n" +
                "   <sign>" + sign + "</sign>\n" +
                "</xml>";

        String result = HttpUtil.post("https://api.mch.weixin.qq.com/pay/micropay", xml, HttpUtil.XML);
        System.out.println(result + "----------------");
        Map resultMap = null;
        try {
            resultMap = doXMLParse(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     */
    public static String createSign(SortedMap<String, String> packageParams) {
        WeixinConfig weixinConfig = SpringContextUtil.getContext().getBean(WeixinConfig.class);
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        String appid = packageParams.get("appId");
        if (StringUtil.isBlank(appid)) {
            appid = packageParams.get("appid");
        }
        if (appid.equals(weixinConfig.getAppId())) {
            sb.append("key=" + weixinConfig.getPartnerKey());
        } else if (appid.equals(weixinConfig.getPublicAppId())) {
            sb.append("key=" + weixinConfig.getPublicPartnerKey());
        }
        System.out.println(sb.toString());
        String sign = StringUtil.toMD5(sb.toString()).toUpperCase();
        return sign;

    }

    /**
     * (微信)获取签名signature
     *
     * @param ticketVo
     * @return
     * @date 2016年01月9日下午1:33:02
     */
    private static Map<String, String> getTargetMap(TicketVo ticketVo, String shareUrl) {
        Map<String, String> valueMap = new LinkedHashMap<String, String>();
        valueMap.put("jsapi_ticket", ticketVo.getTicket());
        valueMap.put("noncestr", UUID.randomUUID().toString());
        valueMap.put("timestamp", Long.toString(System.currentTimeMillis() / 1000));
        valueMap.put("url", shareUrl);

        StringBuffer strBuf = new StringBuffer();
        Iterator it = valueMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry entry = (Map.Entry) it.next();
            strBuf.append("&" + entry.getKey() + "=" + entry.getValue());
        }
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
        }
        md.reset();
        try {
            md.update(strBuf.toString().substring(1).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
        }
        String signature = bytesToHexString(md.digest());
        valueMap.put("signature", signature);
        return valueMap;
    }

    /**
     *
     * byte转为String
     * @param src
     * @return
     * @date 2016年01月6日下午4:48:25
     */
    private static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}