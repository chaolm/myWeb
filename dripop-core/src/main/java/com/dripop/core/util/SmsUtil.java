package com.dripop.core.util;

import com.dripop.core.exception.ServiceException;

/**
 * Created by liyou on 2017/9/18.
 */
public class SmsUtil {

    private static String appId = "10028";
    private static String appkey = "23240165";
    private static String apiAddress = "http://sms.liulianggo.com/sms.php";

    /**
     * 发送短信
     * @param phone
     * @param msgType
     * @param params
     * @return
     * @throws Exception
     */
    public static Boolean send(String phone, MsgTypeInterface msgType, String... params) {
        Long ttl = null;
        if(msgType.getValue().contains("30分钟")) {
            ttl = 1800L;
        }else if(msgType.getValue().contains("10分钟")) {
            ttl = 600L;
        }else {
            ttl = 300L;
        }
        if(RedisUtil.INSTANCE.ttl(phone+msgType.getCodeStr())+60L > ttl) {
            throw new ServiceException("短信发送频繁，请稍后再试");
        }
        String keystr = appkey + appId + phone;
        String sign = StringUtil.toMD5(keystr);
        StringBuffer sb = new StringBuffer();
        if(params.length > 0) {
            for (String param : params) {
                sb.append(param).append("|");
            }
            sb.delete(sb.length()-1, sb.length());
        }
        String postData = "type=pt&app_id="+appId+"&mode_id="+msgType.getCode()+"&sign="+sign+"&vars="+sb.toString()+"&to_phone="+phone;
        String response = HttpUtil.get(apiAddress+"?"+postData);
        String[] results = response.split(",");
        if(results[0].equals("0")) {
            return true;
        }
        return false;
    }
}
