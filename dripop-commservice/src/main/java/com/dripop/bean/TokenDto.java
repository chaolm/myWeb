package com.dripop.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liyou on 2018/1/11.
 */
public class TokenDto implements Serializable {

    private String token;

    private String name;

    private Long orgId;

    private String orgName;
    //权限菜单
    private List<Long> menus;

    public List<Long> getMenus() {
        return menus;
    }

    public void setMenus(List<Long> menus) {
        this.menus = menus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
