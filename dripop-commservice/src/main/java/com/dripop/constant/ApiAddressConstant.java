package com.dripop.constant;

import com.dripop.core.util.SpringContextUtil;

/**
 * Created by liyou on 2018/3/19.
 */
public class ApiAddressConstant {

    public static final String SERVER_API_ROOT = SpringContextUtil.getPropertiesValue("root_url");

    public static final String JJZJ_H5_ROOT = SpringContextUtil.getPropertiesValue("jjzj_h5_root_url");

}
