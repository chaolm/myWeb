package com.dripop.nuonuoinvoice.service;

import com.alibaba.fastjson.JSONObject;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.StringUtil;
import com.dripop.nuonuoinvoice.bean.RequestMode;
import com.dripop.nuonuoinvoice.constants.ExConstants;
import com.dripop.nuonuoinvoice.constants.InvoiceConstants;
import com.dripop.nuonuoinvoice.util.SecurityUtil;

import java.util.Map;

/**
* 提供访问爱信诺开放平台 OpenApiV1 的接口
 * @author sdk.jss.com.cn
 * @version 1.1
 * @since jdk1.6
*/
public class OpenApiV1{


	/**
	 * 执行API调用
	 * @param serverName 服务器的地址
	 * @param headers  请求头参数
	 * @param requestMode 请求体model
	 * @return  返回服务器响应内容
	 * @throws ServiceException
	 */
	public String handle(String serverName,Map<String,String> headers,RequestMode requestMode) throws ServiceException{
		//客户端请求的数据进行基本校验
    	requestDataValidata(serverName,headers, requestMode);
        String dataType = (String)headers.get("dataType");
        String clientReqParam = "";
        String aesParams = "";
		try {
			if ("json".equalsIgnoreCase(dataType)) {
				clientReqParam = installJsonStr(requestMode, clientReqParam);
			}
		} catch (Exception e) {
			throw new ServiceException(ExConstants.input_Data_Type_Msg);
		}
        System.out.println("客户端组装的参数格式-->xml："+clientReqParam);        
        
        
		try {
			//每个APP密钥不同，根据当前APP赋值相应密钥值
			aesParams = (String) SecurityUtil.AESEncrypt(clientReqParam, InvoiceConstants.app_secret);
		} catch (Exception e) {
			throw new ServiceException(ExConstants.security_Encryption_Msg);
		}
		// 发送请求(客户端可以自定义自己的请求)
		HttpClientService httpService = new HttpClientService();
		String result = httpService.sendSyncSingleHttp(serverName, headers, aesParams);
		return result;

    }


	/**
	 * 客户端请求数据校验
	 * @param headers
	 * @param requestMode
	 * @throws ServiceException
	 */
	private void requestDataValidata(String serverName,Map<String, String> headers,
			RequestMode requestMode) throws ServiceException {
		if(StringUtil.isEmpty(serverName)
    			|| (headers == null || headers.isEmpty()) 
    			|| requestMode == null
    			|| requestMode.getPublic() == null 
    			|| requestMode.getPrivate() == null){
    		throw new ServiceException(ExConstants.input_Data_Null_Msg);
    	}
        // 检查header参数不为空
        if (StringUtil.isEmpty(headers.get("appKey")) //appkey
        		|| StringUtil.isEmpty(headers.get("dataType"))// 数据格式<xml/json>
        	    || StringUtil.isEmpty(headers.get("accessToken"))//授权码
        		|| StringUtil.isEmpty(headers.get("appRate"))){
            throw new ServiceException(ExConstants.input_Data_Type_Msg1);
        }
        //加密算法为空默认AES
        if(!StringUtil.isEmpty(headers.get("signMethod")) && !"AES".equals(headers.get("signMethod"))){
        	throw new ServiceException(ExConstants.security_Decryption_arithmeticNULL_Msg);
        }
        //检查public参数不为空
        if(StringUtil.isEmpty(requestMode.getPublic().getMethod())
        		|| StringUtil.isEmpty(requestMode.getPublic().getTimestamp())
        		|| StringUtil.isEmpty(requestMode.getPublic().getVersion())){
        	throw new ServiceException(ExConstants.input_Data_Null_Msg);
        }
        String dataType = (String)headers.get("dataType");
        //检查public参数不为空
        if((!"json".equalsIgnoreCase(dataType)) 
        		&& (!"xml".equalsIgnoreCase(dataType))){
        	throw new ServiceException(ExConstants.input_Data_Type_Msg);
        }
	}


	/**
	 * 组装成JSON格式字符串
	 * @param requestMode
	 * @param clientReqParam
	 * @return
	 */
	private String installJsonStr(RequestMode requestMode, String clientReqParam) throws Exception{
		JSONObject jSONObj = new JSONObject();
		JSONObject publicObj = new JSONObject();
		JSONObject privateObj = new JSONObject();
		publicObj.put("method", requestMode.getPublic().getMethod());
		publicObj.put("version", requestMode.getPublic().getVersion());
		publicObj.put("timestamp", requestMode.getPublic().getTimestamp());
		privateObj.put("servicedata",requestMode.getPrivate().getServicedata());
		jSONObj.put("public",publicObj);
		jSONObj.put("private",privateObj);
		clientReqParam = jSONObj.toString();
		return clientReqParam;
	}
}
