package com.dripop.nuonuoinvoice.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dripop.nuonuoinvoice.util.Compress;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;


/**
 * 发送请求
 * @author sdk.jss.com.cn
 * @version 1.1
 * @since jdk1.6
 */
public class HttpClientService {

	  // 连接超时时间
    private static final int CONNECTION_TIMEOUT = 3000;

    // 读数据超时时间
    private static final int READ_DATA_TIMEOUT = 3000;
    
    private static final int successCode = 200;
	/**
	 * 同步发送方式
	 * @return
	 */
	public String sendSyncSingleHttp(String url,Map<String,String> headers,String param){
		  CloseableHttpClient httpclient = HttpClients.createDefault();
		  RequestConfig defaultRequestConfig = RequestConfig.custom().build();
		  RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
	                .setSocketTimeout(READ_DATA_TIMEOUT)
	                .setConnectTimeout(CONNECTION_TIMEOUT)
	                .build();
		 
          List <NameValuePair> nvps = new ArrayList <NameValuePair>();
          CloseableHttpResponse response = null;
          String compress = (String)headers.get("compress");
        try {
        	HttpPost httpPost = setConnectionParam(url,headers);
        	param = java.net.URLEncoder.encode(param,HTTP.UTF_8);
        	if("GZIP".equals(compress)){
        		param = Compress.compress(param);   //压缩
			}
        	nvps.add(new BasicNameValuePair("param",param));
            UrlEncodedFormEntity utlencode = new UrlEncodedFormEntity(nvps);//HTTP.UTF_8
			httpPost.setEntity(utlencode);	
			httpPost.setConfig(requestConfig);
			response = httpclient.execute(httpPost);
			Header[] heads = httpPost.getAllHeaders();
			for(int i =0;i<heads.length;i++){
				System.out.println(heads[i].getName()+"   "+heads[i].getValue());
			}
			return printResult(response);
		} catch (Exception e) {
//	     	e.printStackTrace();
	     	System.out.println(e.getMessage());
		}finally{
			try {
				if(response!=null){
					response.close();
				}	
				if(httpclient!=null){
					httpclient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	private String printResult( CloseableHttpResponse response){
		String str = "";
		InputStream input = null;;
		if(response.getStatusLine().getStatusCode()==successCode){
			try {
				input = response.getEntity().getContent();
				str = java.net.URLDecoder.decode(inputStreamToString(input),HTTP.UTF_8);
				Header[] heahers = response.getAllHeaders();
				System.out.println("返回消息头：");
				for(Header header : heahers){
					System.out.println(header.getName()+":"+header.getValue());
				}
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					if(input!=null){
						input.close();
					}
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

        }else{
    	  System.out.println(response.getStatusLine().getStatusCode());
        }
		return str;
	}
	
	/**
	 * 构建post请求
	 * @param url
	 * @param headers
	 * @return
	 * @throws IOException
	 * @throws ProtocolException
	 */
	private static HttpPost setConnectionParam(String url,Map<String, String> headers) throws IOException,
			ProtocolException {
		HttpPost hpost = new HttpPost(url);
		Set<String> headerSet = headers.keySet();  
		for(String key:headerSet){  
			 //指定请求header参数  
			hpost.setHeader(key, headers.get(key));  
		}
		return hpost;
	}
	  /**
     * 获取流
     * @return
     * @throws IOException
     */
    public  String inputStreamToString(InputStream input) throws IOException{
    	BufferedReader br = null;
    	String data = "";
    	StringBuffer sb = new StringBuffer();
		br = new BufferedReader(new InputStreamReader(input));
		while((data = br.readLine())!=null){
			sb.append(data);
		}
		return sb.toString();
    }
    
}
