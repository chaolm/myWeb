package com.dripop.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_express")
public class TExpress implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "express_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "description")
    private String description;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "kuaidi100")
    private String kuaidi100;

    @Column(name = "kdniao")
    private String kdniao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getKuaidi100() {
        return kuaidi100;
    }

    public void setKuaidi100(String kuaidi100) {
        this.kuaidi100 = kuaidi100;
    }

    public String getKdniao() {
        return kdniao;
    }

    public void setKdniao(String kdniao) {
        this.kdniao = kdniao;
    }
}
