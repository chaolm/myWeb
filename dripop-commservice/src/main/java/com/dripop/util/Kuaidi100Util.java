package com.dripop.util;

import com.dripop.constant.ApiAddressConstant;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.HttpUtil;
import com.dripop.core.util.JsonUtil;
import com.dripop.entity.TExpress;
import com.dripop.entity.TExpressHistory;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liyou on 2018/4/4.
 */
public class Kuaidi100Util {

    private static final Logger logger = LoggerFactory.getLogger(Kuaidi100Util.class);

    public static final String key = "rqJHskGy8369";
    public static final String subscribe_url = "http://poll.kuaidi100.com/poll";

    public static void subscribe(TExpressHistory expressHistory, Map<Long, TExpress> expressMap) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("callbackurl", ApiAddressConstant.SERVER_API_ROOT+"/callback/expressHistory");
//        parameterMap.put("callbackurl", "http://520937c9.all123.net/service/callback/expressHistory");
        parameterMap.put("salt", expressHistory.getExpressNo());
        map.put("company", expressMap.get(expressHistory.getExpressId()).getKuaidi100());
        map.put("number", expressHistory.getExpressNo());
        map.put("key", key);
        map.put("parameters", parameterMap);
        RequestBody body = new FormBody.Builder()
                .add("schema", "json")
                .add("param", JsonUtil.toJson(map)).build();
        String reslut = HttpUtil.post(subscribe_url, body);
        Map<String, Object> resultMap = JsonUtil.fromJson(reslut);
        if(!resultMap.get("result").toString().equals("true") && !resultMap.get("returnCode").toString().equals("501")) {
            logger.error("kuaidi100 subscribe error : {}， {}", JsonUtil.toJson(expressHistory), resultMap.get("message"));
            throw new ServiceException("快递订阅异常");
        }
    }
}
