package com.dripop.util;

/**
 * Created by liyou on 2018/1/2.
 */
public class PriceUtil {

    /**
     * 获取显示价格字符串
     * @param price
     * @return
     */
    public static String getPriceText(Integer price) {
        if(price == null) {
            return null;
        }
        return String.format("¥%.2f", price * 1.0 / 100);
    }

    /**
     * 获取显示价格字符串
     * @param price
     * @return
     */
    public static String getSimplePriceText(Integer price) {
        if(price == null) {
            return null;
        }
        return String.format("%.2f", price * 1.0 / 100);
    }

    /**
     * 获取显示价格字符串
     * @param price
     * @return
     */
    public static String getSimplePriceText(Object price) {
        if(price == null) {
            return null;
        }
        return String.format("%.2f", Long.parseLong(price.toString()) * 1.0 / 100);
    }

    /**
     * Double转换Integer
     * @param doub
     * @return
     */
    public static Integer doubleToInt(Double doub){
        Double price = doub * 100;
        int b = price.intValue();
        Integer rentCount = Integer.valueOf(b);
        return rentCount;
    }
}
