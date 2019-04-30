package com.dripop.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by liyou on 2017/9/29.
 */
@Entity
@Table(name = "t_version")
public class TVersion {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "app_name")
    private String appName;

    @Column(name = "app_os")
    private String appOs;

    @Column(name = "app_version")
    private String appVersion;

    @Column(name = "must_flag")
    private Integer mustFlag;

    @Column(name = "issure_time")
    private Date issureTime;

    @Column(name = "issure_note")
    private String issureNote;

    @Column(name = "issure_url")
    private String issureUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppOs() {
        return appOs;
    }

    public void setAppOs(String appOs) {
        this.appOs = appOs;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public Integer getMustFlag() {
        return mustFlag;
    }

    public void setMustFlag(Integer mustFlag) {
        this.mustFlag = mustFlag;
    }

    public Date getIssureTime() {
        return issureTime;
    }

    public void setIssureTime(Date issureTime) {
        this.issureTime = issureTime;
    }

    public String getIssureNote() {
        return issureNote;
    }

    public void setIssureNote(String issureNote) {
        this.issureNote = issureNote;
    }

    public String getIssureUrl() {
        return issureUrl;
    }

    public void setIssureUrl(String issureUrl) {
        this.issureUrl = issureUrl;
    }
}
