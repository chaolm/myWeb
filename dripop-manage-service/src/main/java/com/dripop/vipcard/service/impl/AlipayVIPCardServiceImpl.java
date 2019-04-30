package com.dripop.vipcard.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alipay.api.AlipayApiException;
import com.bean.LogicDelete;
import com.dripop.alipay.AlipayConfig;
import com.dripop.alipay.AlipayUtil;
import com.dripop.bean.CommonConstants;
import com.dripop.constant.ApiAddressConstant;
import com.dripop.core.bean.Filter;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.QLBuilder;
import com.dripop.core.util.*;
import com.dripop.dao.CustomerVIPDao;
import com.dripop.dao.StaticDataDao;
import com.dripop.entity.TStaticData;
import com.dripop.vipcard.alipay.AlipayVipCardUtil;
import com.dripop.vipcard.alipay.dto.ColumnInfoList;
import com.dripop.vipcard.alipay.dto.VipCardParam;
import com.dripop.vipcard.dto.VipCardDto;
import com.dripop.vipcard.service.AlipayVIPCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AlipayVIPCardServiceImpl implements AlipayVIPCardService {

    @Autowired
    private StaticDataDao staticDataDao;

    @Autowired
    private CustomerVIPDao vipDao;

    @Transactional
    public void updateVIPCardTemplate(JSONObject reqJson) throws AlipayApiException{

        String cardInfoCache = RedisUtil.INSTANCE.get(CommonConstants.ALIPAY_VIP_CARD);
        //模板更改和创建
        if (cardInfoCache != null) {
            String cardName = reqJson.getString("cardName");
            List<ColumnInfoList> columnInfoList = JsonUtil.fromJson(reqJson.getString("columnInfoList"),
                    new TypeReference<List<ColumnInfoList>>() {
                    });
            getPlatColumnInfo(columnInfoList, CommonConstants.ALIPAY_PLAT_FORM);
            VipCardParam cardParam = JsonUtil.fromJson(cardInfoCache, new TypeReference<VipCardParam>(){});
            //生成requestId
            String requestId = StringUtil.getNormalString();
            //调用支付宝更新会员卡接口
            String templateId = AlipayVipCardUtil.updateVIPCardTemplate(requestId, cardName, cardParam.getTemplateId(), cardParam.getLogoId(),
                    cardParam.getBackgroundId(), columnInfoList);
            //更新卡基础信息
            cardParam.setTemplateId(templateId);
            cardParam.setColumnInfoList(columnInfoList);
            QLBuilder ql = new QLBuilder();
            ql.and(Filter.eq("code", CommonConstants.ALIPAY_VIP_CARD));
            TStaticData staticData = staticDataDao.findOneByJpql(ql);
            staticData.setVal(JSONObject.toJSONString(cardParam));
            staticDataDao.update(staticData);
            RedisUtil.INSTANCE.set(CommonConstants.ALIPAY_VIP_CARD, JSONObject.toJSONString(cardParam), null);
        } else {
            String cardName = reqJson.getString("cardName");
            String logoUrl = reqJson.getString("logoUrl");
            String backgroundUrl = reqJson.getString("backgroundUrl");
            String logoName = reqJson.getString("logoName");
            String logoType = reqJson.getString("logoType");
            String backgroundName = reqJson.getString("backgroundName");
            String backgroundType = reqJson.getString("backgroundType");
            List<ColumnInfoList> columnInfoList = JsonUtil.fromJson(reqJson.getString("columnInfoList"), new TypeReference<List<ColumnInfoList>>() {
            });
            getPlatColumnInfo(columnInfoList, CommonConstants.ALIPAY_PLAT_FORM);
            //调用支付宝上传接口
            String logoString = AlipayUtil.uploadAlipayFile(logoType, logoName, logoUrl);
            Map logoMap = JsonUtil.fromJson(logoString);
            String logoId = (String) logoMap.get("fileId");
            String logoFileUrl = (String) logoMap.get("fileUrl");

            String backgroundString = AlipayUtil.uploadAlipayFile(backgroundType, backgroundName, backgroundUrl);
            Map backgroundMap = JsonUtil.fromJson(backgroundString);
            String backgroundId = (String) backgroundMap.get("fileId");
            String backgroundFileUrl = (String) backgroundMap.get("fileUrl");

            //生成requestId
            String requestId = StringUtil.getNormalString();
            //调用模板生成接口
            String templateId = AlipayVipCardUtil.createVIPCardTemplate(requestId, logoId, cardName, backgroundId, columnInfoList);

            //调用会员卡开卡表单模板配置接口
            AlipayVipCardUtil.cardFormTemplateSet(templateId);

            //会员卡模板开卡借口中回调url拼接https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=APPID&scope=SCOPE&redirect_uri=ENCODED_URL
            //AlipayConfig alipayConfig = SpringContextUtil.getContext().getBean(AlipayConfig.class);
            //String redirectUri = URLEncoder.encode(ApiAddressConstant.SERVER_API_ROOT + "/callback/openVIPCard");
            String callBack = "http://www.jingjie100.com/callback/openVIPCard";

            //获取领卡链接
            String receiveLinkUrl = AlipayVipCardUtil.cardActivateurlApply(templateId, callBack);

            VipCardParam cardParam = new VipCardParam();
            cardParam.setLogoId(logoId);
            cardParam.setLogoUrl(logoFileUrl);
            cardParam.setBackgroundId(backgroundId);
            cardParam.setBackgroundUrl(backgroundFileUrl);
            cardParam.setTemplateId(templateId);
            cardParam.setReceiveLinkUrl(receiveLinkUrl);
            cardParam.setColumnInfoList(columnInfoList);

            TStaticData staticData = new TStaticData();
            staticData.setCode(CommonConstants.ALIPAY_VIP_CARD);
            staticData.setName("支付宝会员卡");
            staticData.setLogicDelete(LogicDelete.NOT_DELETE.getValue());
            staticData.setCreateTime(new Date());
            staticData.setSort(1);
            staticData.setVal(JSONObject.toJSONString(cardParam));
            RedisUtil.INSTANCE.set(CommonConstants.ALIPAY_VIP_CARD, JSONObject.toJSONString(cardParam), null);
            staticDataDao.save(staticData);
        }
    }

    public Pagination<VipCardDto> queryVIPCardRecord(String keyWord, Integer receivePlat, Pageable pageable) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT customer_vip_name as customerVipName, customer_vip_number as customerVipNumber, " +
                " customer_vip_phone as customerVipPhone, receive_time as receiveTime, receive_platform as receivePlatform, " +
                " customer_vip_integration as customerVipIntegration " +
                " from t_customer_vip where 1=1 ");
        if (StringUtil.isNotBlank(keyWord)) {
            sql.append(" and (customer_vip_number=:keyWord OR customer_vip_name=:keyWord OR customer_vip_phone=:keyWord) ");
            params.add(keyWord);
        }
        if (receivePlat != null && receivePlat.intValue() != 0) {
            sql.append(" and receive_platform=:receivePlat ");
            params.add(receivePlat);
        }
        return vipDao.findPageBySql(sql.toString(), pageable, VipCardDto.class, params.toArray());
    }

    private List<ColumnInfoList> getPlatColumnInfo(List<ColumnInfoList> list, String plat) {
        if (StringUtil.isNotBlank(plat)) {
            for (ColumnInfoList columnInfo: list) {
                if (!plat.equals(columnInfo.getPlatForm())) {
                    list.remove(columnInfo);
                }
            }
            return list;
        }
        return null;
    }

}
