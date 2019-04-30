package com.dripop.vipcard.alipay;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.dripop.alipay.AlipayAPIClientFactory;
import com.dripop.alipay.AlipayConfig;
import com.dripop.bean.CommonConstants;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.DateUtil;
import com.dripop.core.util.JsonUtil;
import com.dripop.core.util.SpringContextUtil;
import com.dripop.core.util.StringUtil;
import com.dripop.vipcard.alipay.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AlipayVipCardUtil {

    public static final String REDIRECT_URI = "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?";

    /**
     * 开放表单信息查询业务类型，可选类型如下：
     * MEMBER_CARD -- 会员卡开卡
     */
    private static final String BIZ_TYPE = "MEMBER_CARD";

    /**
     * 会员卡更新接口中卡号ID类型
     * BIZ_CARD -- 会员卡开卡
     */
    private static final String BIZ_CARD = "BIZ_CARD";

    /**
     * 会员卡开发接口中用户ID类型
     * USER_UNI_ID_TYPE -- UID
     */
    private static final String USER_UNI_ID_TYPE = "UID";

    public static final Logger logger = LoggerFactory.getLogger(AlipayVipCardUtil.class);

    private static ColumnInfoList columnInfoList = null;

    private static MoreInfo moreInfo = null;

    private static CardActionList cardAction = null;

    private static FieldRuleList fieldRuleList = null;


    /**
     * 支付宝应用授权获取userId和acessToken
     * @param authCode
     * @return
     */
    public static JSONObject AlipayUserIno(String authCode){
        AlipayConfig alipayConfig = SpringContextUtil.getContext().getBean(AlipayConfig.class);
        AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(alipayConfig.getLifeAppId(), alipayConfig.getPrivateKey(), alipayConfig.getAlipayLifePublicKey());
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(authCode);
        request.setGrantType("authorization_code");
        try {
            AlipaySystemOauthTokenResponse response = alipayClient.execute(request);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", response.getUserId());
            jsonObject.put("acessToken", response.getAccessToken());
            return jsonObject;
        } catch (AlipayApiException e) {
            logger.error("授权失败");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新会员卡模板
     * @param requestId
     * @param templateId
     * @param logoId
     * @param backgroundId
     * @param columnInfoList
     * @return
     * @throws AlipayApiException
     */
    public static  String updateVIPCardTemplate(String requestId, String cardName, String templateId, String logoId,
                                                String backgroundId, List<ColumnInfoList> columnInfoList) throws AlipayApiException{
        AlipayConfig alipayConfig = SpringContextUtil.getContext().getBean(AlipayConfig.class);
        AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(alipayConfig.getLifeAppId(), alipayConfig.getPrivateKey(), alipayConfig.getAlipayLifePublicKey());
        AlipayMarketingCardTemplateModifyRequest request = new AlipayMarketingCardTemplateModifyRequest();

        JSONObject baseRequestInfo = new JSONObject();
        baseRequestInfo.put("request_id", requestId);
        baseRequestInfo.put("template_id", templateId);
        baseRequestInfo.put("write_off_type", "qrcode");
        JSONObject templateStyleInfo = new JSONObject();
        templateStyleInfo.put("card_show_name", cardName);
        templateStyleInfo.put("background_id", backgroundId);
        templateStyleInfo.put("bg_color", "rgb(55,112,179)");
        templateStyleInfo.put("logo_id", logoId);
        baseRequestInfo.put("template_style_info", templateStyleInfo);
        baseRequestInfo.put("field_rule_list", createFieldRuleList());
        baseRequestInfo.put("column_info_list", columnInfoList);
        baseRequestInfo.put("card_action_list", createCardActionList());

        request.setBizContent(baseRequestInfo.toJSONString());
        AlipayMarketingCardTemplateModifyResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            logger.info("会员卡模板更新调用成功："+response);
        } else {
            logger.error("会员卡模板更新调用失败"+response);
            throw new ServiceException("会员卡模板更新调用失败");
        }
        return response.getTemplateId();
    }

    /**
     * 查询用户提交的模板信息
     * @param templateId
     * @param requestId
     * @param acessToken
     * @throws AlipayApiException
     */
    public static String queryActivateForm(String templateId, String requestId, String acessToken) throws AlipayApiException{
        AlipayConfig alipayConfig = SpringContextUtil.getContext().getBean(AlipayConfig.class);
        AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(alipayConfig.getLifeAppId(), alipayConfig.getPrivateKey(), alipayConfig.getAlipayLifePublicKey());
        AlipayMarketingCardActivateformQueryRequest request = new AlipayMarketingCardActivateformQueryRequest();
        JSONObject param = new JSONObject();
        param.put("biz_type", "MEMBER_CARD");
        param.put("template_id", templateId);
        param.put("request_id", requestId);
        request.setBizContent(param.toJSONString());
        AlipayMarketingCardActivateformQueryResponse response = alipayClient.execute(request,acessToken);
        if(response.isSuccess()){
            logger.info("查询用户提交的模板信息调用成功："+response);
        } else {
            logger.error("查询用户提交的模板信息调用失败"+response);
            throw new ServiceException("查询用户提交的模板信息调用失败");
        }
        return response.getInfos();
    }

    /**
     * 获取会员卡详情页地址
     * @param userCardNo
     * @return
     * @throws AlipayApiException
     */
    public static String getVIPCardLink(String userCardNo)throws AlipayApiException {

        AlipayConfig alipayConfig = SpringContextUtil.getContext().getBean(AlipayConfig.class);
        AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(alipayConfig.getLifeAppId(),
                alipayConfig.getPrivateKey(), alipayConfig.getAlipayLifePublicKey());
        AlipayMarketingCardQueryRequest request = new AlipayMarketingCardQueryRequest();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("target_card_no", userCardNo);
        jsonObject.put("target_card_no_type", "BIZ_CARD");
        request.setBizContent(jsonObject.toJSONString());
        AlipayMarketingCardQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            logger.info("获取会员卡详情页地址调用成功："+response);
        } else {
            logger.error("获取会员卡详情页地址调用失败"+response);
            throw new ServiceException("获取会员卡详情页地址调用失败");
        }
        return response.getSchemaUrl();
    }

    /**
     * 会员卡更新
     * @param point 积分变动
     * @param templateId 模板ID
     * @param targetCardNo 目标卡号
     * @param openDate 会员卡创建时间
     * @param validDate 有效时间
     * @throws AlipayApiException
     */
    public static void updateVIPCard(String point, String templateId, String targetCardNo, Date openDate, String validDate)throws AlipayApiException{
        AlipayConfig alipayConfig = SpringContextUtil.getContext().getBean(AlipayConfig.class);
        AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(alipayConfig.getLifeAppId(), alipayConfig.getPrivateKey(), alipayConfig.getAlipayLifePublicKey());
        AlipayMarketingCardUpdateRequest request = new AlipayMarketingCardUpdateRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("target_card_no", targetCardNo);
        bizContent.put("target_card_no_type", BIZ_CARD);
        bizContent.put("occur_time", DateUtil.getDate(new Date(), DateUtil.DATE_FORMAT_YMDHMS));
        JSONObject cardInfo = new JSONObject();
        cardInfo.put("open_date", openDate);
        cardInfo.put("valid_date", validDate);
        cardInfo.put("point", point);
        cardInfo.put("template_id", templateId);
        bizContent.put("card_info", cardInfo);
        AlipayMarketingCardUpdateResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            logger.info("会员卡更新调用成功："+response);
        } else {
            logger.error("会员卡更新调用失败"+response);
            throw new ServiceException("会员卡更新调用失败");
        }
    }

    /**
     * 支付宝会员卡开卡接口
     * @param templateId 模板ID
     * @param userUniId 支付宝用户ID
     * @throws AlipayApiException
     */
    public static JSONObject createVIPCard(String templateId, String userUniId, String acessToken) throws AlipayApiException{
        AlipayConfig alipayConfig = SpringContextUtil.getContext().getBean(AlipayConfig.class);
        AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(alipayConfig.getLifeAppId(), alipayConfig.getPrivateKey(), alipayConfig.getAlipayLifePublicKey());
        AlipayMarketingCardOpenRequest request = new AlipayMarketingCardOpenRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_serial_no", StringUtil.getNormalString());
        bizContent.put("card_template_id", templateId);
        JSONObject cardUserInfo = new JSONObject();
        cardUserInfo.put("user_uni_id", userUniId);
        cardUserInfo.put("user_uni_id_type", USER_UNI_ID_TYPE);
        bizContent.put("card_user_info", cardUserInfo);
        JSONObject cardExtInfo = new JSONObject();
        cardExtInfo.put("external_card_no", StringUtil.getNormalString());
        cardExtInfo.put("open_date", DateUtil.dateFormat(new Date(), DateUtil.DATE_FORMAT_YMDHMS));
        cardExtInfo.put("valid_date", DateUtil.getDateAddDay(DateUtil.getFormattedDate(new Date(), DateUtil.DATE_FORMAT_YMDHMS), 365*50, DateUtil.DATE_FORMAT_YMDHMS));
        cardExtInfo.put("point", "0");
        bizContent.put("card_ext_info", cardExtInfo);
        request.setBizContent(bizContent.toJSONString());
        AlipayMarketingCardOpenResponse response = alipayClient.execute(request, acessToken);
        if(response.isSuccess()){
            logger.info("支付宝会员卡开卡接口调用成功："+response);
        } else {
            logger.error("支付宝会员卡开卡接口调用失败"+response);
            throw new ServiceException("支付宝会员卡开卡接口调用失败");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("alipayVipNumber", response.getCardInfo().getBizCardNo());
        jsonObject.put("customerVipNumber", response.getCardInfo().getExternalCardNo());
        jsonObject.put("openDate", response.getCardInfo().getOpenDate());
        jsonObject.put("point", response.getCardInfo().getPoint());
        jsonObject.put("receivePlatform", CommonConstants.ALIPAY_PLAT_FORM);

        return jsonObject;
    }

    /**
     * 会员提交表单查询
     * @param templateId
     * @param requestId
     * @return
     * @throws AlipayApiException
     */
    public static String cardActivateformQuery(String templateId, String requestId) throws AlipayApiException{
        AlipayConfig alipayConfig = SpringContextUtil.getContext().getBean(AlipayConfig.class);
        AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(alipayConfig.getLifeAppId(), alipayConfig.getPrivateKey(), alipayConfig.getAlipayLifePublicKey());
        AlipayMarketingCardActivateformQueryRequest request = new AlipayMarketingCardActivateformQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("biz_type", BIZ_TYPE);
        bizContent.put("template_id", templateId);
        bizContent.put("request_id", requestId);
        AlipayMarketingCardActivateformQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            logger.info("会员提交表单查询调用成功："+response);
        } else {
            logger.error("会员提交表单查询调用失败"+response);
            throw new ServiceException("会员提交表单查询调用失败");
        }
        return response.getInfos();
    }

    /**
     *
     * 获取会员卡领卡投放链接
     *
     * @param templateId 模板ID
     * @param callback 会员卡开卡表单提交后回调地址
     * @return
     * @throws AlipayApiException
     */
    public static String cardActivateurlApply(String templateId, String callback) throws AlipayApiException{
        AlipayConfig alipayConfig = SpringContextUtil.getContext().getBean(AlipayConfig.class);
        AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(alipayConfig.getLifeAppId(), alipayConfig.getPrivateKey(), alipayConfig.getAlipayLifePublicKey());
        AlipayMarketingCardActivateurlApplyRequest request = new AlipayMarketingCardActivateurlApplyRequest();

        JSONObject bizContent = new JSONObject();
        bizContent.put("template_id", templateId);
        bizContent.put("callback", callback);
        request.setBizContent(bizContent.toJSONString());
        AlipayMarketingCardActivateurlApplyResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            logger.info("获取会员卡领卡投放链接调用成功："+response);
        } else {
            logger.error("获取会员卡领卡投放链接调用失败"+response);
            throw new ServiceException("获取会员卡领卡投放链接调用失败");
        }
        return response.getApplyCardUrl();
    }

    /**
     *
     * 会员卡开卡表单模板配置
     *
     * @param templateId 模板id
     * @throws AlipayApiException
     */
    public static void cardFormTemplateSet(String templateId) throws AlipayApiException{
        AlipayConfig alipayConfig = SpringContextUtil.getContext().getBean(AlipayConfig.class);
        AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(alipayConfig.getLifeAppId(),
                alipayConfig.getPrivateKey(), alipayConfig.getAlipayLifePublicKey());
        AlipayMarketingCardFormtemplateSetRequest request = new AlipayMarketingCardFormtemplateSetRequest();
        JSONObject bizParams = new JSONObject();
        bizParams.put("template_id", templateId);
        ArrayList<String> requiredFields = new ArrayList<String>();
        //用户名
        requiredFields.add("OPEN_FORM_FIELD_NAME");
        //性别
        requiredFields.add("OPEN_FORM_FIELD_GENDER");
        //手机号码
        requiredFields.add("OPEN_FORM_FIELD_MOBILE");
        //常驻城市
        requiredFields.add("OPEN_FORM_FIELD_CITY");

        //可选字段
        ArrayList<String> optionalFields = new ArrayList<String>();
        //邮箱
        optionalFields.add("OPEN_FORM_FIELD_EMAIL");

        JSONObject fields = new JSONObject();
        JSONObject requiredField = new JSONObject();
        requiredField.put("common_fields", requiredFields);

        JSONObject optionalField = new JSONObject();
        optionalField.put("common_fields", optionalFields);

        fields.put("required", requiredField);
        fields.put("optional", optionalField);
        bizParams.put("fields", fields);

        request.setBizContent(bizParams.toJSONString());
        AlipayMarketingCardFormtemplateSetResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            logger.info("会员卡开卡表单模板配置调用成功："+response);
        } else {
            logger.error("会员卡开卡表单模板配置调用失败"+response);
            throw new ServiceException("会员卡开卡表单模板配置调用失败");
        }
    }

    /**
     *
     * 支付宝会员卡创建模板
     *
     * @param logoId logoID
     * @param backgroundId 背景图ID
     * @return
     * @throws AlipayApiException
     */
    public static String createVIPCardTemplate(String requestId, String logoId, String cardName, String backgroundId, List<ColumnInfoList> columnInfoList) throws AlipayApiException {
        AlipayConfig alipayConfig = SpringContextUtil.getContext().getBean(AlipayConfig.class);
        AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(alipayConfig.getLifeAppId(), alipayConfig.getPrivateKey(), alipayConfig.getAlipayLifePublicKey());
        AlipayMarketingCardTemplateCreateRequest request = new AlipayMarketingCardTemplateCreateRequest();
        AlipayVipCardReq requestReq = new AlipayVipCardReq();
        //基础配置参数
        requestReq.setRequest_id(requestId);
        requestReq.setCard_type("OUT_MEMBER_CARD"); //支付宝文档固定值
        requestReq.setBiz_no_prefix(StringUtil.getRadomString(10, 2));
        requestReq.setBiz_no_suffix_len("10");
        requestReq.setWrite_off_type("qrcode");
        //会员卡背景参数
        TemplateStyleInfo templateStyleInfo = new TemplateStyleInfo();
        templateStyleInfo.setCard_show_name(cardName);
        templateStyleInfo.setBackground_id(backgroundId);
        templateStyleInfo.setBg_color("rgb(55,112,179)");
        templateStyleInfo.setLogo_id(logoId);
        requestReq.setTemplate_style_info(templateStyleInfo);
        //跳转栏
        //List<ColumnInfoList> columnInfoList = createColumnInfoList();
        requestReq.setColumn_info_list(columnInfoList);
        //自定义卡行动点配置(去付款按钮和自助下单按钮)
        List<CardActionList> cardActionList = createCardActionList();
        requestReq.setCard_action_list(cardActionList);
        requestReq.setField_rule_list(createFieldRuleList());
        request.setBizContent(JsonUtil.toJson(requestReq));
        //调用SDK
        AlipayMarketingCardTemplateCreateResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            logger.info("会员卡创建模板调用成功："+response);
        } else {
            logger.error("会员卡创建模板调用失败"+response);
            throw new ServiceException("会员卡创建模板调用失败");
        }

        return response.getTemplateId();
    }

    private static List<FieldRuleList> createFieldRuleList() {
        List<FieldRuleList> list = new ArrayList<>();
        fieldRuleList = new FieldRuleList();
        fieldRuleList.setField_name("Point");
        fieldRuleList.setRule_name("ASSIGN_FROM_REQUEST");
        fieldRuleList.setRule_value("Point");
        list.add(fieldRuleList);
        fieldRuleList = new FieldRuleList();
        fieldRuleList.setField_name("OpenDate");
        fieldRuleList.setRule_name("ASSIGN_FROM_REQUEST");
        fieldRuleList.setRule_value("OpenDate");
        list.add(fieldRuleList);
        fieldRuleList = new FieldRuleList();
        fieldRuleList.setField_name("ValidDate");
        fieldRuleList.setRule_name("ASSIGN_FROM_REQUEST");
        fieldRuleList.setRule_value("ValidDate");
        list.add(fieldRuleList);
        return list;
    }

    private static List<CardActionList> createCardActionList() {
        List<CardActionList> list = new ArrayList<>();
        cardAction = new CardActionList();
        cardAction.setCode("TO_PAYMENT_IN");
        cardAction.setText("我的订单");
        cardAction.setUrl("https://m.jingjie100.com");//晶杰之家待支付订单url链接
        list.add(cardAction);
        cardAction = new CardActionList();
        cardAction.setCode("TO_PAY_ORDER_IN");
        cardAction.setText("晶杰之家");
        cardAction.setUrl("https://m.jingjie100.com");//晶杰之家首页url链接
        list.add(cardAction);
        return list;
    }

    private static List<ColumnInfoList> createColumnInfoList() {
        List<ColumnInfoList> list = new ArrayList<>();
        //会员详情栏
        columnInfoList = new ColumnInfoList();
        columnInfoList.setCode("VIP_DETAIL");
        columnInfoList.setOperate_type("openNative");
        columnInfoList.setTitle("会员详情");
        columnInfoList.setValue("立即查看");
        moreInfo = new MoreInfo();
        moreInfo.setTitle("会员卡详情");
        String[] descs = {"特权说明","会员消费1元赠1积分","电话","4001037773","注意事项","每人限领一张，请在 支付宝-卡包 进行查看"};
        moreInfo.setDescs(descs);
        columnInfoList.setMore_info(moreInfo);
        list.add(columnInfoList);

        //生活号栏
        columnInfoList = new ColumnInfoList();
        columnInfoList.setCode("LIFE_NUMBER");
        columnInfoList.setOperate_type("openWeb");
        columnInfoList.setTitle("生活号");
        columnInfoList.setValue("立即进入");
        moreInfo = new MoreInfo();
        moreInfo.setTitle("生活号详情");
        moreInfo.setUrl("");//晶杰之家生活号链接
        columnInfoList.setMore_info(moreInfo);
        list.add(columnInfoList);

        //领券中心
        columnInfoList = new ColumnInfoList();
        columnInfoList.setCode("COUPON_CENTER");
        columnInfoList.setOperate_type("openWeb");
        columnInfoList.setTitle("领券中心");
        columnInfoList.setValue("立即领取");
        moreInfo = new MoreInfo();
        moreInfo.setTitle("领券中心详情");
        moreInfo.setUrl("");//晶杰之家领券中心链接
        columnInfoList.setMore_info(moreInfo);
        list.add(columnInfoList);

        //我的卡券
        columnInfoList = new ColumnInfoList();
        columnInfoList.setCode("MY_COUPON");
        columnInfoList.setOperate_type("openWeb");
        columnInfoList.setTitle("我的卡券");
        columnInfoList.setValue("立即查看");
        moreInfo = new MoreInfo();
        moreInfo.setTitle("我的卡券详情");
        moreInfo.setUrl("");//晶杰之家我的卡券链接
        columnInfoList.setMore_info(moreInfo);
        list.add(columnInfoList);

        //我的订单
        columnInfoList = new ColumnInfoList();
        columnInfoList.setCode("MY_ORDER");
        columnInfoList.setOperate_type("openWeb");
        columnInfoList.setTitle("我的订单");
        columnInfoList.setValue("立即查看");
        moreInfo = new MoreInfo();
        moreInfo.setTitle("我的订单详情");
        moreInfo.setUrl("");//晶杰之家我的订单链接
        columnInfoList.setMore_info(moreInfo);
        list.add(columnInfoList);

        //售后服务
        columnInfoList = new ColumnInfoList();
        columnInfoList.setCode("AFTER_SALE");
        columnInfoList.setOperate_type("openWeb");
        columnInfoList.setTitle("售后服务");
        columnInfoList.setValue("立即查看");
        moreInfo = new MoreInfo();
        moreInfo.setTitle("售后服务详情");
        moreInfo.setUrl("");//晶杰之家售后服务链接
        columnInfoList.setMore_info(moreInfo);
        list.add(columnInfoList);

        //个人资料
        columnInfoList = new ColumnInfoList();
        columnInfoList.setCode("CUSTOMER_CENTER");
        columnInfoList.setOperate_type("openWeb");
        columnInfoList.setTitle("个人资料");
        columnInfoList.setValue("完善资料");
        moreInfo = new MoreInfo();
        moreInfo.setTitle("个人资料详情");
        moreInfo.setUrl("");//个人资料
        columnInfoList.setMore_info(moreInfo);
        list.add(columnInfoList);

        //晶杰之家门店
        columnInfoList = new ColumnInfoList();
        columnInfoList.setCode("DRIP_STORE");
        columnInfoList.setOperate_type("openWeb");
        columnInfoList.setTitle("晶杰之家门店");
        columnInfoList.setValue("");
        moreInfo = new MoreInfo();
        moreInfo.setTitle("晶杰之家门店");
        moreInfo.setUrl("");//晶杰之家门店url链接
        columnInfoList.setMore_info(moreInfo);
        list.add(columnInfoList);

        return list;
    }



}
