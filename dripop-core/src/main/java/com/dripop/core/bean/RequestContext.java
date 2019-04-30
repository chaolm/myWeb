package com.dripop.core.bean;

import java.io.Serializable;

/**
 * Created by liyou on 2017/10/19.
 */
public class RequestContext implements Serializable {

    private String basePath;

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
