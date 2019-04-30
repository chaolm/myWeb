package com.dripop.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by liyou on 2017/9/21.
 */
@Entity
@Table(name = "t_static_data")
public class TStaticData implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    private String code;
    private String name;
    private String val;

    @Column(name = "extend_params")
    private String extendParams;

    private Integer sort;

    @Column(name = "logic_delete")
    private Integer logicDelete;

    @Column(name = "create_time")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getExtendParams() {
        return extendParams;
    }

    public void setExtendParams(String extendParams) {
        this.extendParams = extendParams;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getLogicDelete() {
        return logicDelete;
    }

    public void setLogicDelete(Integer logicDelete) {
        this.logicDelete = logicDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
