package com.dripop.sys.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dripop.entity.TCard;

import java.util.Date;

public class InterDtoWXCustomer implements java.io.Serializable {

	private Long id;

	private String name;//姓名

	private String phoneNo;//手机号码

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;//创建时间

	private Integer sex;//性别

	private String wechat;//微信号

	private String qq;

	private Integer buyNumber;

	private Integer buyMoney;

	//等级积分

	private Integer pointValue;

	private String headimgUrl;

	private String wechatNickName;

	private String qqNickName;



	public void setWechatNickName(String wechatNickName) {
		this.wechatNickName = wechatNickName;
	}

	public String getHeadimgUrl() {
		return headimgUrl;
	}

	public void setHeadimgUrl(String headimgUrl) {
		this.headimgUrl = headimgUrl;
	}

	/*用户查询条件 截至时间
   * */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

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

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getWechat() {
		if(wechatNickName!=null){
			return wechatNickName;
		}
		else return "";

	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getQq() {
       if(qqNickName!=null){
       	return qqNickName;
	   }
		return "";
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Integer getBuyNumber() {
		return buyNumber;
	}
 
	public void setQqNickName(String qqNickName) {
		this.qqNickName = qqNickName;
	}

	public void setBuyNumber(Integer buyNumber) {
		this.buyNumber = buyNumber;
	}

	public Integer getBuyMoney() {
		return buyMoney;
	}

	public void setBuyMoney(Integer buyMoney) {
		this.buyMoney = buyMoney;
	}

	public Integer getPointValue() {
		return pointValue;
	}

	public void setPointValue(Integer pointValue) {
		this.pointValue = pointValue;
	}
}