package com.dripop.core.util;

import com.dripop.core.exception.SystemException;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by liyou on 2017/9/5.
 */
public class HttpUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

    private static OkHttpClient client = new OkHttpClient.Builder().sslSocketFactory(getSLLContext().getSocketFactory()).hostnameVerifier(new HostnameVerifier() {
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    }).build();

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType XML = MediaType.parse("text/xml; charset=utf-8");
    public static final MediaType TEXT = MediaType.parse("text/html; charset=utf-8");
    public static final MediaType WWW_FORM = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    public static String get(String url) {
        return get(url, "", JSON);
    }

    public static byte[] getByte(String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().bytes();
        }catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    public static String get(String url, String json) {
        return get(url, json, JSON);
    }

    public static <T> T get(String url, String json, Class<T> clz) {
        return JsonUtil.fromJson(get(url, json), clz);
    }

    public static String get(String url, String json, MediaType mediaType) {
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    public static String post(String url, String json) {
        return post(url, json, JSON);
    }

    public static String post(String url, String json, MediaType mediaType) {
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    public static String post(String url, RequestBody body) {
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        }catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    public static <T> T  post(String url, String json, Class<T> clz) {
        return JsonUtil.fromJson(post(url, json), clz);
    }

    public static SSLContext getSLLContext() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            log.error("系统异常",e);
        } catch (KeyManagementException e) {
            log.error("系统异常",e);
        }
        return  sslContext;
    }

    public static void main(String[] args) throws Exception {
        HttpUtil.post("http://api.dripop.com/serivce?token=111", "");
    }

}
