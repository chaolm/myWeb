package com.dripop.constant;

/**
 * redis缓存key
 *
 * @author dq
 * @date 2018/4/13 10:10
 */

public class RedisKey {
    /*广告图 key + code */
    public static final String STATIC_DATE_IMG_KEY = "STATIC_DATE_IMG_KEY_";
    //随机验证码 + uuid
    public static final String STATIC_DATE_RANDOM_KEY = "STATIC_DATE_RANDOM_KEY_";

    //首页类目json
    public static final String STATIC_DATA_LAYOUT_KEY = "STATIC_DATA_LAYOUT_KEY_";

    /*首页专区 key + id*/
    public static final String INDEX_KEY = "INDEX_KEY_";

    /*商品分类 key */
    public static final String GOODS_CLASS_KEY = "GOODS_CLASS_KEY";

    /*商品详情 key + onlineId */
    public static final String GOODS_ONLINE_DETAIL_KEY = "GOODS_ONLINE_DETAIL_KEY_";

    public static final String HOT_SEARCH = "HOT_SEARCH";

    public static final String CACHE_REFRESH_TIME = "CACHE_REFRESH_TIME";
}
