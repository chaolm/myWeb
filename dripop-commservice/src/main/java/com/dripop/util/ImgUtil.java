package com.dripop.util;

/**
 * Created by liyou on 2018/1/3.
 */
public class ImgUtil {

    private static final String fwfh300x200 = "!/fwfh/300x200";
    private static final String fw350 = "!/fw/350";
    private static final String fw250 = "!/fw/250";
    private static final String fh200 = "!/fh/200";
    private static final String p1 = "!p1";

    /**
     * 处理图片宽高
     * @param imgUrl
     * @return
     */
    public static String getFwfh300x200(String imgUrl) {
        if(imgUrl == null) {
            return null;
        }
        if(imgUrl.endsWith(fwfh300x200)) {
            return imgUrl;
        }
        return imgUrl + fwfh300x200;
    }

    /**
     * 处理图片宽高
     * @param imgUrl
     * @return
     */
    public static String getFw350(String imgUrl) {
        if(imgUrl == null) {
            return null;
        }
        if(imgUrl.endsWith(fw350)) {
           return imgUrl;
        }
        return imgUrl + fw350;
    }

    /**
     * 处理图片宽高
     * @param imgUrl
     * @return
     */
    public static String getFw250(String imgUrl) {
        if(imgUrl == null) {
            return null;
        }
        if(imgUrl.endsWith(fw250)) {
            return imgUrl;
        }
        return imgUrl + fw250;
    }

    /**
     * 处理图片宽高
     * @param imgUrl
     * @return
     */
    public static String getFh200(String imgUrl) {
        if(imgUrl == null) {
            return null;
        }
        if(imgUrl.endsWith(fh200)) {
            return imgUrl;
        }
        return imgUrl + fh200;
    }

    /**
     * 处理图片宽高
     * @param imgUrl
     * @return
     */
    public static String getP1(String imgUrl) {
        if(imgUrl == null) {
            return null;
        }
        if(imgUrl.endsWith(p1)) {
            return imgUrl;
        }
        return imgUrl + p1;
    }
}
