package com.dripop.interceptor;

import com.dripop.core.exception.RestException;
import com.dripop.core.interceptor.BaseInterceptor;
import com.dripop.core.util.SessionUtil;
import com.dripop.core.util.StringUtil;
import com.dripop.dto.InterDtoUser;
import com.dripop.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liyou on 2017/9/22.
 */

@Component
public class OpInterceptor implements BaseInterceptor {

    private static final Logger log = LoggerFactory.getLogger(OpInterceptor.class);

    private String interStartTimeKey = "inter_start_time";

    private String CONTENT_TYPE = "application/json";

    @Value("${app_sign_key}")
    private String appSignKey;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if(HttpStatus.NOT_FOUND.value() == response.getStatus()) {
            throw new RestException("请求地址不存在，请稍后再试");
        }
        if(request.getServletPath().indexOf("/hd") >= 0) {
            return true;
        }
        String time = request.getParameter("time");
        String sign = request.getParameter("sign");
        if(StringUtil.isBlank(time, sign)) {
            throw new RestException("缺失公共参数");
        }
        request.setAttribute(interStartTimeKey, new Date());
        BufferedReader br = new BufferedReader(new InputStreamReader(SessionUtil.getRequest().getInputStream(), "utf-8"));
        String line = null;
        StringBuffer reqJson = new StringBuffer();
        while ((line = br.readLine()) != null) {
            reqJson.append(line);
        }
        br.close();
        String token = request.getParameter("token");
        if(StringUtil.isNotBlank(token)){
            request.setAttribute(UserUtil.MANAGE_TOKEN_REQ_KEY, token);
        }
        if(!isOpenUrl(request) && UserUtil.currentAdminUser() == null) {
            throw new RestException(499, "用户token已失效");
        }
        //校验签名
        Long currentTime = Calendar.getInstance().getTimeInMillis() / 1000;
        Long intfTime = Long.parseLong(request.getParameter("time"));
        if(Math.abs(currentTime - intfTime) > 5 * 60) {
            throw new RestException("请求失败");
        }
        String signStr = "time" + time + "token" + token + reqJson.toString() + appSignKey;
        signStr = signStr.replaceAll(" ", "");
        signStr = signStr.replaceAll(" ", "");
        if(!sign.equals(StringUtil.toMD5(signStr))) {
            throw new RestException("校验签名失败");
        }
        if(!validPermission(request)) {
            throw new RestException(498, "对不起，您暂无访问权限");
        }
        if(UserUtil.currentAdminUser() != null){
            UserUtil.adminUserExpireReset();
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
/*
if(HttpStatus.INTERNAL_SERVER_ERROR.value() == response.getStatus()) {
            modelAndView.setViewName("/screen/500");
        } else if(HttpStatus.NOT_FOUND.value() == response.getStatus()) {
            modelAndView.setViewName("/screen/404");
        }
*/

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {

    }

    private Boolean isOpenUrl(HttpServletRequest request) {
        return request.getServletPath().indexOf("/open") >= 0
                || request.getServletPath().equals("/error") ? true : false;
    }

/**
     * 是否为App h5页面链接
     * @param request
     * @return

**/


    private Boolean isMobileUrl(HttpServletRequest request) {
        return request.getServletPath().startsWith("/mobile") ? true : false;
    }

    /**
     * 校验用户权限
     * @return
     */
    private boolean validPermission(HttpServletRequest request) {
        if(isOpenUrl(request)) {
            return true;
        }
        if(request.getServletPath().indexOf("/common") >= 0 ||
                request.getServletPath().indexOf("/brand/list") >= 0 ||
                request.getServletPath().indexOf("/goods/goodsList") >= 0 ||
                request.getServletPath().indexOf("/type/listType") >= 0) {
            return true;
        }
        InterDtoUser userDto = UserUtil.currentAdminUser();
        String currentPath = ","+request.getServletPath()+",";
        for (String permission : userDto.getPermissions()) {
            for (String url : permission.split(",")) {
                if(StringUtil.match(".*?,"+url.replaceAll("\\*", ".*")+",.*?", currentPath)) {
                    return true;
                }
            }
        }
        return false;
    }
}

