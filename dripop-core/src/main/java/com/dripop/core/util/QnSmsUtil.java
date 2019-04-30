package com.dripop.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QnSmsUtil {

    private static Logger log = LoggerFactory.getLogger(QnSmsUtil.class);

    private static String appId = SpringContextUtil.getPropertiesValue("sms.appId");
    private static String appkey = SpringContextUtil.getPropertiesValue("sms.appKey");
    private static String apiAddress = SpringContextUtil.getPropertiesValue("sms.api.address");

    public static Boolean send(String phone, MsgTypeInterface msgType, String... params) throws Exception {
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
