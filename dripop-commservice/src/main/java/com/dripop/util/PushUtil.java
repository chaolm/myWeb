package com.dripop.util;

import com.bean.MsgServiceType;
import com.dripop.core.util.SpringContextUtil;
import com.dripop.core.util.StringUtil;
import com.dripop.core.util.ThreadUtil;
import com.dripop.entity.TSysmsg;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PushUtil {

	private static Logger logger = LoggerFactory.getLogger(PushUtil.class);

    private static final String toc_appId = SpringContextUtil.getPropertiesValue("getui_toc_appId");
    private static final String toc_appkey = SpringContextUtil.getPropertiesValue("getui_toc_appkey");
    private static final String toc_master = SpringContextUtil.getPropertiesValue("getui_toc_master");
    private static final String toc_host = "http://sdk.open.api.igexin.com/apiex.htm";

    public static void pushSingle(final TSysmsg sysMsg, final String clientId) {
        if(StringUtil.isBlank(clientId)) {
            return;
        }
        ThreadUtil.INSTANCE.excute(new Runnable() {
            @Override
            public void run() {
                IGtPush push = new IGtPush(toc_host, toc_appkey, toc_master);

                TransmissionTemplate template = transmissionTemplateDemo(sysMsg, sysMsg.getChannel(), toc_appId, toc_appkey);
                logger.info("clientId:" + clientId + ",clientOS:" + sysMsg.getChannel() + ",个推推送内容:"+template.getTransmissionContent());
                SingleMessage message = new SingleMessage();
                message.setOffline(true);
                //离线有效时间，单位为毫秒，可选
                message.setOfflineExpireTime(24 * 3600 * 1000);
                message.setData(template);
                message.setPushNetWorkType(0); //可选。判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
                Target target = new Target();

                target.setAppId(toc_appId);
                target.setClientId(clientId);
                IPushResult ret = null;
                try{
                    ret = push.pushMessageToSingle(message, target);
                }catch(RequestException e){
                    logger.error("推送失败",e);
                    ret = push.pushMessageToSingle(message, target, e.getRequestId());
                }
                if(ret != null){
                    logger.info(ret.getResponse().toString());
                }else{
                    logger.info("服务器响应异常");
                }
            }
        });
    }

    public static void pushAll(final TSysmsg sysMsg) {
        ThreadUtil.INSTANCE.excute(new Runnable() {
            @Override
            public void run() {
                IGtPush push = new IGtPush(toc_host, toc_appkey, toc_master);

                TransmissionTemplate template = transmissionTemplateDemo(sysMsg, sysMsg.getChannel(), toc_appId, toc_appkey);
                logger.info("clientOS:" + sysMsg.getChannel() + ",个推推送内容:"+template.getTransmissionContent());
                AppMessage message = new AppMessage();
                message.setOffline(true);
                //离线有效时间，单位为毫秒，可选
                message.setOfflineExpireTime(24 * 3600 * 1000);
                message.setData(template);
                message.setPushNetWorkType(0); //可选。判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。

                //推送给App的目标用户需要满足的条件
                List<String> appIdList = new ArrayList<String>();
                appIdList.add(toc_appId);
                message.setAppIdList(appIdList);

                IPushResult ret = null;
                try{
                    ret = push.pushMessageToApp(message);
                }catch(RequestException e){
                    logger.error("推送失败",e);
                }
                if(ret != null){
                    logger.info(ret.getResponse().toString());
                }else{
                    logger.info("服务器响应异常");
                }
            }
        });
    }

//	public static void pushGetuiC(TSysPush sysPush, String clientId) {
//		String host,appkey,master,appId;
//		host = toc_host;
//		appkey = toc_appkey;
//		master = toc_master;
//		appId = toc_appId;
//
//		IGtPush push = new IGtPush(host, appkey, master);
//
//		TransmissionTemplate template = transmissionTemplateDemoC(sysPush, appId,appkey);
//		logger.error("clientId:" + clientId + ",个推推送内容:"+template.getTransmissionContent());
//		SingleMessage message = new SingleMessage();
//		message.setOffline(true);
//		//离线有效时间，单位为毫秒，可选
//		message.setOfflineExpireTime(24 * 3600 * 1000);
//		message.setData(template);
//		message.setPushNetWorkType(0); //可选。判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
//		Target target = new Target();
//
//		target.setAppId(appId);
//		target.setClientId(clientId);
//		IPushResult ret = null;
//		try{
//			ret = push.pushMessageToSingle(message, target);
//		}catch(RequestException e){
//			logger.error("推送失败",e);
//			ret = push.pushMessageToSingle(message, target, e.getRequestId());
//		}
//		if(ret != null){
//			logger.info(ret.getResponse().toString());
//		}else{
//			logger.info("服务器响应异常");
//		}
//	}


	public static void main(String [] args) {
//		TSysPush sysPush = new TSysPush();
//		sysPush.setLink("link");
//		sysPush.setTitle("title");
//		String clientId = "018314777c2924ed21eb9e0434d89680";
//		String host,appkey,master,appId;
//		host = toc_host;
//		appkey = toc_appkey;
//		master = toc_master;
//		appId = toc_appId;
//
//		IGtPush push = new IGtPush(host, appkey, master);
//
//		TransmissionTemplate template = transmissionTemplateDemoC(sysPush, appId,appkey);
//		logger.error("clientId:" + clientId + ",个推推送内容:"+template.getTransmissionContent());
//		SingleMessage message = new SingleMessage();
//		message.setOffline(true);
//		//离线有效时间，单位为毫秒，可选
//		message.setOfflineExpireTime(24 * 3600 * 1000);
//		message.setData(template);
//		message.setPushNetWorkType(0); //可选。判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
//		Target target = new Target();
//
//		target.setAppId(appId);
//		target.setClientId(clientId);
//		IPushResult ret = null;
//		try{
//			ret = push.pushMessageToSingle(message, target);
//		}catch(RequestException e){
//			logger.error("推送失败",e);
//			ret = push.pushMessageToSingle(message, target, e.getRequestId());
//		}
//		if(ret != null){
//			logger.info(ret.getResponse().toString());
//		}else{
//			logger.info("服务器响应异常");
//		}

        TSysmsg sysmsg = null;
        sysmsg = new TSysmsg();
        sysmsg.setChannel(1);
        sysmsg.setRelId("1000200002232");
        sysmsg.setServiceType(MsgServiceType.GOODS.getValue());
        sysmsg.setTitle("您关注的商品已经到货，快去抢购！");
        sysmsg.setContent("你关注的“xxx”已经到货");
//        PushUtil.pushSingle(sysmsg, "24a5cdff8e73522d01254431c593684b");
//        PushUtil.pushAll(sysmsg);
	}

    public static TransmissionTemplate transmissionTemplateDemo(TSysmsg sysMsg, Integer clientOS, String appId, String appkey) {
    	TransmissionTemplate template = new TransmissionTemplate();
    	template.setAppId(appId);
    	template.setAppkey(appkey);
    	// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
    	String payLoad = getPayload(sysMsg, clientOS);
    	
    	template.setTransmissionType(2);
    	String transmissionContent = "{\"title\":\"晶杰之家\",\"content\":\""+sysMsg.getTitle()+"\",\"payload\":"+payLoad+"}";
    	template.setTransmissionContent(transmissionContent);
    	APNPayload apnpayload = new APNPayload();
        apnpayload.setSound("");
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setTitle("晶杰之家");
        alertMsg.setBody(sysMsg.getTitle());
        alertMsg.setTitleLocKey("晶杰之家");
        alertMsg.setActionLocKey(payLoad);
        apnpayload.setAlertMsg(alertMsg);
        apnpayload.setBadge(1);
        template.setAPNInfo(apnpayload);
    	return template;
    }
    
    // 构建不同消息下，不同手机操作系统下的payload值
    private static String getPayload(TSysmsg sysMsg,Integer clientOS){
    	String ret = "{\"mode\":1,\"url\":\""+LinkUtil.getAppLinkUrl(sysMsg.getServiceType(), sysMsg.getRelId())+"\"}";

//    	if (clientOS == null){
//    		return ret;
//    	}
//    	// android
//    	if (clientOS == 0){
//			switch (sysMsg.getType()){
//				case TSysmsg.SYSMSG_TYPE_OF_CUSTOMER_BIND:
//					ret = "{\"mode\":1,\"url\":\"/customerDetail?custId="+sysMsg.getParams()+"\"}";
//					break;
//				case TSysmsg.SYSMSG_TYPE_OF_CUSTOMER_UNBIND:
//					ret = "{\"mode\":1, \"url\":\"/messageList\",\"rel_id\":\""+sysMsg.getParams()+"\",\"type\":\"15\"}";
//					break;
//				case TSysmsg.SYSMSG_TYPE_OF_ORDER_CANCEL_APPLY:
//					ret = "{\"mode\":1, \"url\":\"/orderDetail?cancelOrderId="+sysMsg.getParams()+"\"}";
//					break;
//				case TSysmsg.SYSMSG_TYPE_OF_NEWTASK:
//				case TSysmsg.SYSMSG_TYPE_OF_ENDTASK:
//					ret = "{\"mode\":1, \"url\":\"/taskList\"}";
//					break;
//				case TSysmsg.SYSMSG_TYPE_OF_ORDER_WAIT_PAY:
//				case TSysmsg.SYSMSG_TYPE_OF_ORDER_WAIT_DELIVER:
//				case TSysmsg.SYSMSG_TYPE_OF_ORDER_WAIT_PICK:
//				case TSysmsg.SYSMSG_TYPE_OF_SERVICE_HANDLE_FAIL:
//					ret = "{\"mode\":1, \"url\":\"/orderDetail?orderId="+ sysMsg.getParams() +"\"}";
//					break;
//				case TSysmsg.SYSMSG_TYPE_OF_ORDER_PAY_SUCCESS_TO_CASHIER:
//					ret = "{\"mode\":1, \"url\":\"/orderPay?orderId="+ sysMsg.getParams() +"\"}";
//					break;
//				case TSysmsg.SYSMSG_TYPE_OF_ORDER_PAY_SUCCESS_TO_SALESMAN:
//					ret = "{\"mode\":1, \"url\":\"/paySuccess?orderId="+ sysMsg.getParams() +"\"}";
//					break;
//				case TSysmsg.SYSMSG_TYPE_OF_ORDER_REMIND_DELIVER:
//					ret = "{\"mode\":1, \"url\":\"/orderDetail?orderId="+ sysMsg.getParams() +"\"}";
//					break;
//				case TSysmsg.TOC_NOTIFY_ORDER_PAY:
//				case TSysmsg.TOC_NOTIFY_ORDER_PAY_SUCCESS:
//				case TSysmsg.TOC_NOTIFY_ORDER_PICK_SUCCESS:
//				case TSysmsg.TOC_NOTIFY_ORDER_DELIVER_SUCCESS:
//				case TSysmsg.TOC_NOTIFY_ORDER_RECEIVE_SUCCESS:
//					ret = "{\"mode\":1, \"url\":\"xfq2c://order/detail?id="+ sysMsg.getParams() +"\"}";
//					break;
//				case TSysmsg.TOC_NOTIFY_ORDER_CANCEL_FAIL:
//				case TSysmsg.TOC_NOTIFY_ORDER_CANCEL_SUCCESS:
//					ret = "{\"mode\":1, \"url\":\"xfq2c://order/cancel_detail?id="+ sysMsg.getParams() +"\"}";
//					break;
//				case TSysmsg.TOC_NOTIFY_CARD_GET:
//					ret = "{\"mode\":1, \"url\":\"xfq2c://card/center\"}";
//					break;
//				case TSysmsg.SYSMSG_TYPE_OF_SK_ORDER_PAY_SUCCESS_TO_SALESMAN:
//					ret = "{\"mode\":1, \"url\":\"/paysuccssdetail?orderId="+ sysMsg.getParams() +"\"}";
//					break;
//				case TSysmsg.SYSMSG_TYPE_OF_PRESELL_ORDER_HAVE_PAY_CLOSE:
//					ret = "{\"mode\":1,\"url\":\"/preSaleOderDetail?presellOrderId="+sysMsg.getParams()+"\"}";
//					break;
//			}
//
//    	}else{
//    		// ios
//			switch (sysMsg.getType()){
//				case TSysmsg.SYSMSG_TYPE_OF_CUSTOMER_BIND:
//					ret = "{\"mode\":1,\"url\":\"/customerDetail?custId="+sysMsg.getParams()+"\"}";
//					break;
//				case TSysmsg.SYSMSG_TYPE_OF_CUSTOMER_UNBIND:
//					ret = "{\"mode\":1, \"url\":\"/messageList\",\"rel_id\":\""+sysMsg.getParams()+"\",\"type\":\"15\"}";
//					break;
//				case TSysmsg.SYSMSG_TYPE_OF_ORDER_CANCEL_APPLY:
//					ret = "{\"mode\":1, \"url\":\"/orderDetail?cancelOrderId="+sysMsg.getParams()+"\"}";
//					break;
//				case TSysmsg.SYSMSG_TYPE_OF_NEWTASK:
//				case TSysmsg.SYSMSG_TYPE_OF_ENDTASK:
//					ret = "{\"mode\":1, \"url\":\"/taskList\"}";
//					break;
//				case TSysmsg.SYSMSG_TYPE_OF_ORDER_WAIT_PAY:
//				case TSysmsg.SYSMSG_TYPE_OF_ORDER_WAIT_DELIVER:
//				case TSysmsg.SYSMSG_TYPE_OF_ORDER_WAIT_PICK:
//				case TSysmsg.SYSMSG_TYPE_OF_SERVICE_HANDLE_FAIL:
//					ret = "{\"mode\":1, \"url\":\"/orderDetail?orderId="+ sysMsg.getParams() +"\"}";
//					break;
//				case TSysmsg.SYSMSG_TYPE_OF_ORDER_PAY_SUCCESS_TO_CASHIER:
//					ret = "{\"mode\":1, \"url\":\"/orderPay?orderId="+ sysMsg.getParams() +"\"}";
//					break;
//				case TSysmsg.SYSMSG_TYPE_OF_ORDER_PAY_SUCCESS_TO_SALESMAN:
//					ret = "{\"mode\":1, \"url\":\"/paySuccess?orderId="+ sysMsg.getParams() +"\"}";
//					break;
//				case TSysmsg.SYSMSG_TYPE_OF_ORDER_REMIND_DELIVER:
//					ret = "{\"mode\":1, \"url\":\"/orderDetail?orderId="+ sysMsg.getParams() +"\"}";
//					break;
//				case TSysmsg.TOC_NOTIFY_ORDER_PAY:
//				case TSysmsg.TOC_NOTIFY_ORDER_PAY_SUCCESS:
//				case TSysmsg.TOC_NOTIFY_ORDER_PICK_SUCCESS:
//				case TSysmsg.TOC_NOTIFY_ORDER_DELIVER_SUCCESS:
//				case TSysmsg.TOC_NOTIFY_ORDER_RECEIVE_SUCCESS:
//					ret = "{\"mode\":1, \"url\":\"xfq2c://order/detail?id="+ sysMsg.getParams() +"\"}";
//					break;
//				case TSysmsg.TOC_NOTIFY_ORDER_CANCEL_FAIL:
//				case TSysmsg.TOC_NOTIFY_ORDER_CANCEL_SUCCESS:
//					ret = "{\"mode\":1, \"url\":\"xfq2c://order/cancel_detail?id="+ sysMsg.getParams() +"\"}";
//					break;
//				case TSysmsg.TOC_NOTIFY_CARD_GET:
//					ret = "{\"mode\":1, \"url\":\"xfq2c://card/center\"}";
//					break;
//				case TSysmsg.SYSMSG_TYPE_OF_SK_ORDER_PAY_SUCCESS_TO_SALESMAN:
//					ret = "{\"mode\":1, \"url\":\"/paysuccssdetail?orderId="+ sysMsg.getParams() +"\"}";
//					break;
//				case TSysmsg.SYSMSG_TYPE_OF_PRESELL_ORDER_HAVE_PAY_CLOSE:
//					ret = "{\"mode\":1,\"url\":\"/preSaleOderDetail?presellOrderId="+sysMsg.getParams()+"\"}";
//					break;
//			}
//    	}
    	
    	return ret;
    }
}
