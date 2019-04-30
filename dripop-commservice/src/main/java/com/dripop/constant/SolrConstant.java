package com.dripop.constant;

import com.dripop.core.util.SpringContextUtil;

/**
 * solr常量
 *
 * @author dq
 * @date 2018/6/28 14:48
 */

public class SolrConstant {

    public static final String SOLR_URL = SpringContextUtil.getPropertiesValue("solr_url");
}
