package com.dripop.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * TCoupon entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_sysmsg")
public class TSysmsg implements java.io.Serializable {

	@Id
	@GeneratedValue
	private Long id;

	private String title;

	private String content;

	private Integer type;

	@Column(name = "service_type")
	private Integer serviceType;

	@Column(name = "recive_id")
	private Long reciveId;

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "expire_time")
	private Date expireTime;

	private Integer status;

    @Column(name = "rel_id")
	private String relId;

	private Integer channel;

    @Column(name = "start_time")
	private Date startTime;

    private String link;

    @Column(name = "is_repeal")
    private Integer isRepeal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public Long getReciveId() {
        return reciveId;
    }

    public void setReciveId(Long reciveId) {
        this.reciveId = reciveId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRelId() {
        return relId;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getIsRepeal() {
        return isRepeal;
    }

    public void setIsRepeal(Integer isRepeal) {
        this.isRepeal = isRepeal;
    }
}