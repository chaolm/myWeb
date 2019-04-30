package com.dripop.core.util;

import com.dripop.core.constant.BaseConstant;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by liyou on 2017/9/5.
 */
public class SessionUtil {

    public static void setRequest(String name, Object value) {
        getRequest().setAttribute(name, value);
    }

    public static <T> T getRequest(String name, Class<T> clz) {
        Object obj = getRequest().getAttribute(name);
        return obj == null ? null : (T) obj;
    }

    public static void setSession(String name, Object value) {
        getRequest().getSession().setAttribute(name, value);
    }

    public static <T> T getSession(String name, Class<T> clz) {
        Object obj = getRequest().getSession().getAttribute(name);
        return obj == null ? null : (T) obj;
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static String getCookieByName(String name) {
        Cookie[] cookies = getRequest().getCookies();
        if(cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public static String restReqBody() {
        return getRequest(BaseConstant.REST_REQ_BODY_KEY, String.class);
    }

    public static String restRespBody() {
        return getRequest(BaseConstant.REST_RESP_BODY_KEY, String.class);
    }

    /**
     * 获得真实IP地址
     * @return
     */
    public static String getIpAddr() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) { // IPv4和IPv6的localhost
            // 客户端和服务端是在同一台机器上、获取本机的IP
            InetAddress inet = null;
            try {
                inet = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            ip = inet.getHostAddress();
        }
        return ip;
    }

    /**
     * 判断是否为手机浏览器访问
     * @return
     */
    public static boolean isMobile() {
        boolean isMoblie = false;
        String[] mobileAgents = { "iphone", "android", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
                "opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
                "nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
                "docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
                "techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
                "wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
                "pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
                "240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
                "blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
                "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
                "mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
                "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
                "smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tsm-", "upg1", "upsi", "vk-v",
                "voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
                "Googlebot-Mobile" };
        String ua = SessionUtil.getRequest().getHeader("User-Agent");
        if (StringUtil.isNotBlank(ua)) {
            ua = ua.toLowerCase();
            for(int i=0;i<mobileAgents.length;i++){
                if(ua.indexOf(mobileAgents[i]) >= 0){
                    isMoblie = true;
                    break;
                }
            }
            if(!isMoblie && ua.indexOf("micromessenger") > 0){
                isMoblie = true;
            }
        }

        return isMoblie;
    }

    /**
     * 判断是否是微信内置浏览器
     * @return
     */
    public static boolean isWechat() {
        String ua = SessionUtil.getRequest().getHeader("user-agent").toLowerCase();
        return ua.indexOf("micromessenger") > 0;
    }

    /**
     * 判断是否是支付宝内置浏览器
     * @return
     */
    public static boolean isAlipay() {
        String ua = SessionUtil.getRequest().getHeader("user-agent");
        if (StringUtil.isNotBlank(ua)) {
            ua = ua.toLowerCase();
            return ua.indexOf("alipayclient") > 0;
        }else{
            return false;
        }
    }
}
