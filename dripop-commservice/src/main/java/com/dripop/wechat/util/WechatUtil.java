package com.dripop.wechat.util;

import com.alibaba.fastjson.JSONObject;
import com.dripop.bean.CommonConstants;
import com.dripop.core.util.*;
import com.dripop.wechat.bean.TicketVo;
import com.dripop.wechat.bean.TokenVo;
import com.dripop.wechat.bean.WeChatConfigVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by liyou on 2017/10/19.
 */
public class WechatUtil {

    public final static String APP_ID = SpringContextUtil.getPropertiesValue("wechat_appid");
    public final static String APP_SECRET = SpringContextUtil.getPropertiesValue("wechat_appsecret");

    //这个地址是腾讯提供的
    private static String tokenServiceUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
    //这个地址是腾讯提供的
    private static String ticketServiceUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

    public final static String WECHAT_ACCESS_TOKEN = "CIRCLE_WECHAT_ACCESS_TOKEN";
    public final static String WECHAT_ACCESS_TICKET = "CIRCLE_WECHAT_ACCESS_TICKET";

    /**
     * 判断是否是微信内置浏览器
     * @return
     */
    public static boolean isWechat() {
        String ua = SessionUtil.getRequest().getHeader("user-agent").toLowerCase();
        return ua.indexOf("micromessenger") > 0;
    }

    /**
     * 获取当前用户openid
     * @return
     * @throws Exception
     */
    public static String getOpenId() throws Exception {
        return getOpenId(SessionUtil.getRequest(), SessionUtil.getResponse(), false);
    }

    public static String getOpenId(HttpServletRequest request, HttpServletResponse response, Boolean needAuth) throws Exception {
        String scope = "snsapi_base";
        if(needAuth) {
            scope = "snsapi_userinfo";
        }
        String redirectUrl = CommonConstants.ROOT_URL + request.getRequestURI();
        if(StringUtil.isNotBlank(request.getQueryString())) {
            redirectUrl = redirectUrl + "?" + request.getQueryString();
        }
        String authUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+APP_ID+"&redirect_uri="+ URLEncoder.encode(redirectUrl,"utf-8")+"&response_type=code&scope="+scope+"&state=STATE#wechat_redirect";
        String openId = request.getSession().getAttribute("wechat_openid") == null ? null : request.getSession().getAttribute("wechat_openid").toString();
//        logger.info("get wechat openId : {}", openId);
        if(StringUtil.isBlank(openId)) {//本地未获取过openid
            String[] codes = request.getParameterValues("code");
            if(codes == null) {
                response.sendRedirect(authUrl);
                return null;
            }
            String code = codes[codes.length-1];
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+APP_ID+"&secret="+APP_SECRET+"&code="+code+"&grant_type=authorization_code";
            String jsonStr = HttpUtil.post(url, "");
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            if(StringUtil.isNotBlank(jsonObject.getString("errcode"))) {
                response.sendRedirect(authUrl);
                return null;
            } else {
                openId = jsonObject.getString("openid");
                request.getSession().setAttribute("wechat_openid",openId);
            }
        }
        request.getSession().setAttribute("wechat_openid",openId);
        return openId;
    }

    /**
     * 微信分享需要初始化一些数据，详情见前段js文件
     * 把这个对象的四个字段填充到js页面
     * @return
     */
    public static WeChatConfigVo getWeChatConfig(String appId, String appSecret, String shareUrl) throws Exception{
        //(微信)获取access_token
        TokenVo tokenVo = getTokenVo(appId,appSecret);

        //(微信)获取ticket【根据tokenVo获取ticketVo】
        TicketVo ticketVo = getTicketVo(tokenVo);

        //(微信)获取签名signature
        Map<String, String> valueMap = getTargetMap(ticketVo,shareUrl);

        WeChatConfigVo configVo = new WeChatConfigVo();
        configVo.setWeChatAppId(appId);
        configVo.setWeChatNonceStr(valueMap.get("noncestr"));
        configVo.setWeChatSignature(valueMap.get("signature"));
        configVo.setWeChatTimestamp(valueMap.get("timestamp"));
        return configVo;
    }

    /**
     * (微信)获取access_token
     * @return
     * @date 2016年01月9日下午1:31:56
     */
    private static TokenVo getTokenVo(String appId, String appSecret) throws Exception{
        TokenVo tokenVo = RedisUtil.INSTANCE.get(WECHAT_ACCESS_TOKEN, TokenVo.class);
        if(tokenVo == null || StringUtil.isBlank(tokenVo.getAccess_token())) {
            Map<String, String> tokenValueMap = new HashMap<String, String>();
            tokenValueMap.put("appid",appId);
            tokenValueMap.put("secret",appSecret);
            tokenVo = JsonUtil.fromJson(HttpUtil.post(tokenServiceUrl, JsonUtil.toJson(tokenValueMap)), TokenVo.class);
            if(tokenVo != null && StringUtil.isNotBlank(tokenVo.getAccess_token())) {
                RedisUtil.INSTANCE.setnx(WECHAT_ACCESS_TOKEN, JsonUtil.toJson(tokenVo), 6);
            }
        }
        return tokenVo;
    }

    /**
     * (微信)获取ticket
     * @param tokenVo
     * @return
     * @date 2016年01月9日下午1:32:32
     */
    private static TicketVo getTicketVo(TokenVo tokenVo) throws Exception{
        TicketVo ticketVo = RedisUtil.INSTANCE.get(WECHAT_ACCESS_TICKET, TicketVo.class);
        if(ticketVo == null || StringUtil.isBlank(ticketVo.getTicket())) {
            Map<String, String> ticketValueMap = new HashMap<String, String>();
            ticketValueMap.put("access_token", tokenVo.getAccess_token());
            ticketValueMap.put("type", "jsapi");
            ticketVo = JsonUtil.fromJson(HttpUtil.post(ticketServiceUrl, JsonUtil.toJson(ticketValueMap)), TicketVo.class);
            if(ticketVo != null && StringUtil.isNotBlank(ticketVo.getTicket())) {
                RedisUtil.INSTANCE.setnx(WECHAT_ACCESS_TICKET, JsonUtil.toJson(ticketVo), 6);
            }
        }
        return ticketVo;
    }

    /**
     * (微信)获取签名signature
     * @param ticketVo
     * @return
     * @date 2016年01月9日下午1:33:02
     */
    private static Map<String, String> getTargetMap(TicketVo ticketVo,String shareUrl){
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
