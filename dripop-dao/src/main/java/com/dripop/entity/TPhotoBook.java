package com.dripop.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@javax.persistence.Entity
@Table(name = "t_photo_book")
public class TPhotoBook implements java.io.Serializable{

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "type")
	private Integer type;

	@Column(name = "ref_id")
	private Long refId;

	@Column(name = "img_url")
	private String imgUrl;

	@Column(name = "modify_date")
	private Date modify_date;
	public TPhotoBook(Long id, Integer type, Long refId, String imgUrl, Date modify_date) {
		super();
		this.id = id;
		this.type = type;
		this.refId = refId;
		this.imgUrl = imgUrl;
		this.modify_date = modify_date;
	}
	public TPhotoBook() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getRefId() {
		return refId;
	}
	public void setRefId(Long refId) {
		this.refId = refId;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Date getModify_date() {
		return modify_date;
	}
	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}
	
	
}