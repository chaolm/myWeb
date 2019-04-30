package com.dripop.sys.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liyou on 2018/1/3.
 */
public class OpPermissionMenuDto implements Serializable {

    private Long menuId;

    private String title;

    @JSONField(serialize = false)
    private Long parentId;

    private Long permissionId;

    private List<OpPermissionMenuDto> children;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public List<OpPermissionMenuDto> getChildren() {
        return children;
    }

    public void setChildren(List<OpPermissionMenuDto> children) {
        this.children = children;
    }
}
