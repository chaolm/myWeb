package com.dripop.controller;

import com.alibaba.fastjson.JSONObject;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.controller.BaseController;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.JsonUtil;
import com.dripop.core.util.RedisUtil;
import com.dripop.core.util.SpringContextUtil;
import com.dripop.core.util.StringUtil;
import com.dripop.huodong.HuodongService;
import com.dripop.sys.service.SysOrgService;
import com.dripop.wechat.bean.TokenVo;
import com.dripop.weixin.WeixinConfig;
import com.dripop.weixin.WeixinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liyou on 2018/4/27.
 */
@Controller
@RequestMapping("hd")
public class HuodongController extends BaseController {

    @Autowired
    private HuodongService huodongService;

    @PostMapping("yuyue")
    @ResponseBody
    public ResultInfo yuyue(String phone, String address) {
        try {
            if(StringUtil.isNotBlank(phone) && Long.parseLong(phone) > 0) {
                huodongService.yuyue(phone, address);
            }else {
                throw new ServiceException("手机号格式不正确");
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("该手机号已经预约");
        }
        return returnSuccess();
    }

    @RequestMapping("activeYear")
    @ResponseBody
    public ResultInfo activeYear(String phone, String openId, String gj, String yj) {
        if(StringUtil.isBlank(phone, openId)) {
            throw new ServiceException("缺失必要参数");
        }
        if(!StringUtil.isMobile(phone)){
            throw new ServiceException("请填写正确手机格式");
        }
        if(RedisUtil.INSTANCE.get(openId) == null) {
            throw new ServiceException("服务繁忙，请进入活动首页重新参与活动");
        }
        huodongService.activeYear(phone, openId);
        return returnSuccess();
    }

    @RequestMapping("isSubscribe")
    @ResponseBody
    public ResultInfo isSubscribe(String code, String redirectUrl) {
        JSONObject jsonObject = WeixinUtil.getAccessToken(code);
        System.out.println(jsonObject.toJSONString());
        String openId = jsonObject.getString("openid");
        String accessToken = jsonObject.getString("access_token");
        WeixinConfig weixinConfig = SpringContextUtil.getContext().getBean(WeixinConfig.class);
        TokenVo tokenVo = WeixinUtil.getTokenVo(weixinConfig.getPublicAppId(), weixinConfig.getPublicAppSecret());
        System.out.println(JsonUtil.toJson(tokenVo));
        Integer isSubscribe = WeixinUtil.isSubscribe(tokenVo.getAccess_token(), openId);
        Map<String, Object> map = new HashMap<>();
        map.put("openId", openId);
        RedisUtil.INSTANCE.set(openId, openId, 24*60*1000);
        map.put("isSubscribe", isSubscribe);
        return returnSuccess(map);
    }

    @RequestMapping("weixinConfig")
    @ResponseBody
    public ResultInfo weixinConfig(String redirectUrl) {
        System.out.println(redirectUrl);
        System.out.println(URLDecoder.decode(redirectUrl));
        WeixinConfig weixinConfig = SpringContextUtil.getContext().getBean(WeixinConfig.class);
        Map<String, Object> map = new HashMap<>();
        map.put("configVo", WeixinUtil.getWeChatConfig(weixinConfig.getPublicAppId(), weixinConfig.getPublicAppSecret(), redirectUrl));
        return returnSuccess(map);
    }

    @RequestMapping("qiusai")
    @ResponseBody
    public ResultInfo qiusai(String phone, String openId, String gj, String yj) {
        if(StringUtil.isBlank(phone, openId, gj, yj)) {
            throw new ServiceException("缺失必要参数");
        }
        if(RedisUtil.INSTANCE.get(openId) == null) {
            throw new ServiceException("服务繁忙，请进入活动首页重新参与活动");
        }
        huodongService.qiusai(phone, openId, gj, yj);
        return returnSuccess();
    }
}
